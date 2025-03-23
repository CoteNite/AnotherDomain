package cn.cotenite.ratelimter.interceptor

import cn.cotenite.asp.Slf4j
import cn.cotenite.asp.Slf4j.Companion.log
import cn.cotenite.expection.BusinessException
import cn.cotenite.ratelimter.annotation.ConcurrentRateLimiter
import cn.cotenite.ratelimter.bean.BHConcurrentRateLimiter
import cn.cotenite.ratelimter.policy.ConcurrentRateLimiterPolicy
import cn.hutool.core.util.StrUtil
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.EnvironmentAware
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ThreadPoolExecutor


/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/23 07:10
 */
@Slf4j
@Aspect
@Component
@ConditionalOnProperty(prefix = "rate.limit.local.concurrent", name = ["enabled"], havingValue = "true")
class ConcurrentRateLimiterInterceptor(
    @Value("\${rate.limit.local.concurrent.default.corePoolSize:3}")
    val defaultCorePoolSize:Int,

    @Value("\${rate.limit.local.concurrent.default.maximumPoolSize:5}")
    val defaultMaximumPoolSize:Int,

    @Value("\${rate.limit.local.concurrent.default.queueCapacity:10}")
    val defaultQueueCapacity:Int,

    @Value("\${rate.limit.local.concurrent.default.keepAliveTime:30}")
    val defaultKeepAliveTime:Long,

    @Value("\${rate.limit.local.concurrent.default.timeout:1}")
    val defaultTimeOut:Long,

    private var environment: Environment
): EnvironmentAware, DisposableBean{

    private val  BH_CONCURRENT_RATE_LIMITER_MAP = ConcurrentHashMap<String, BHConcurrentRateLimiter> ()

    @Pointcut("@annotation(concurrentRateLimiter)")
    fun pointCut( concurrentRateLimiter: ConcurrentRateLimiter){

    }

    @Around(value = "pointCut(concurrentRateLimiter)")
    @Throws(Throwable::class)
    fun around(pjp: ProceedingJoinPoint, concurrentRateLimiter: ConcurrentRateLimiter): Any {
        val signature = pjp.signature as MethodSignature
        val className = pjp.target.javaClass.simpleName
        val methodName = signature.name
        var rateLimitName = environment.resolvePlaceholders(concurrentRateLimiter.name)
        if (StrUtil.isEmpty(rateLimitName) || rateLimitName.contains("\${")) {
            rateLimitName = "$className-$methodName"
        }
        val rateLimiter: BHConcurrentRateLimiter = this.getRateLimiter(rateLimitName, concurrentRateLimiter)
        val args = pjp.args

        return rateLimiter.submit<Any> {
            try {
                return@submit pjp.proceed(args)
            } catch (e: Throwable) {
                if (e is BusinessException) {
                    throw e
                }
                throw RuntimeException(e)
            }
        }

    }

    private fun getRateLimiter(rateLimitName:String, concurrentRateLimiter:ConcurrentRateLimiter): BHConcurrentRateLimiter{
        var bhRateLimiter = BH_CONCURRENT_RATE_LIMITER_MAP[rateLimitName]
        if (bhRateLimiter == null){
            val finalRateLimitName = rateLimitName.intern()
            synchronized (finalRateLimitName){
                bhRateLimiter = BH_CONCURRENT_RATE_LIMITER_MAP[rateLimitName]
                if (bhRateLimiter == null){
                    val corePoolSize =
                        if(concurrentRateLimiter.corePoolSize <= 0)
                            defaultCorePoolSize
                        else
                            concurrentRateLimiter.corePoolSize
                    val maximumPoolSize =
                        if (concurrentRateLimiter.maximumPoolSize <= 0)
                            defaultMaximumPoolSize
                        else
                            concurrentRateLimiter.maximumPoolSize
                    val queueCapacity =
                        if (concurrentRateLimiter.queueCapacity <= 0)
                            defaultQueueCapacity
                        else
                            concurrentRateLimiter.queueCapacity

                    val keepAliveTime =
                        if (concurrentRateLimiter.keepAliveTime<= 0)
                            defaultKeepAliveTime
                        else
                            concurrentRateLimiter.keepAliveTime

                    val keepAliveTimeUnit = concurrentRateLimiter.keepAliveTimeUnit

                    val timeout =
                        if(concurrentRateLimiter.timeout <= 0)
                            defaultTimeOut
                        else concurrentRateLimiter.timeout

                    val timeoutUnit = concurrentRateLimiter.timeoutTimeUnit

                    val executor = ThreadPoolExecutor(corePoolSize,
                        maximumPoolSize,
                        keepAliveTime,
                        keepAliveTimeUnit,
                        ArrayBlockingQueue(queueCapacity),
                        { Thread (it, "rate-limiter-threadPool-$rateLimitName-") },
                        ConcurrentRateLimiterPolicy()
                    )
                    bhRateLimiter = BHConcurrentRateLimiter(executor, timeout, timeoutUnit)
                    BH_CONCURRENT_RATE_LIMITER_MAP.putIfAbsent(rateLimitName, bhRateLimiter!!)
                }
            }
        }
        return bhRateLimiter!!
    }

    override fun setEnvironment(environment: Environment) {
        this.environment = environment
        val map = BH_CONCURRENT_RATE_LIMITER_MAP

        if (map.size > 0) {
            for ((_, rateLimiter) in map) {
                rateLimiter.shutdown()
            }
            map.clear()
            BH_CONCURRENT_RATE_LIMITER_MAP.clear()
        }
    }

    override fun destroy() {
        log.info("destroy|关闭线程池")
    }


}