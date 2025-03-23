package cn.cotenite.ratelimter.interceptor

import cn.cotenite.asp.Slf4j
import cn.cotenite.asp.Slf4j.Companion.log
import cn.cotenite.expection.BusinessException
import cn.cotenite.ratelimter.annotation.AnotherDomainRateLimiter
import cn.cotenite.ratelimter.bean.BHRateLimiter
import cn.hutool.core.util.StrUtil
import com.google.common.util.concurrent.RateLimiter
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.EnvironmentAware
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap


/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/23 05:27
 */
@Slf4j
@Aspect
@Component
@ConditionalOnProperty(prefix = "rate.limit.local", name = ["enabled"], havingValue = "true")
class RateLimiterInterceptor(
    @Value("\${rate.limit.local.default.permitsPerSecond:1000}")
    val defaultPermitsPerSecond: Long,
    @Value("\${rate.limit.local.default.timeout:1}")
    val defaultTimeout: Long,
    private var environment: Environment
): EnvironmentAware {

    companion object{
        private val BH_RATE_LIMITER_MAP = ConcurrentHashMap<String, BHRateLimiter>()
    }

    @Pointcut("@within(cn.cotenite.ratelimter.annotation.AnotherDomainRateLimiter)")
    fun classLevelAnnotation() {}


    @Pointcut("@annotation(cn.cotenite.ratelimter.annotation.AnotherDomainRateLimiter)")
    fun methodLevelAnnotation() {}

    @Pointcut("classLevelAnnotation() || methodLevelAnnotation()")
    fun combinedPointcut() {}

    @Around("combinedPointcut()")
    @Throws(Throwable::class)
    fun around(pjp: ProceedingJoinPoint): Any? {
        val signature = pjp.signature as MethodSignature
        val className = pjp.target.javaClass.simpleName
        val methodName = signature.name

        val methodAnnotation = signature.method.getAnnotation(AnotherDomainRateLimiter::class.java)
        val classAnnotation = pjp.target.javaClass.getAnnotation(AnotherDomainRateLimiter::class.java)
        val finalAnnotation = methodAnnotation ?: classAnnotation

        if (finalAnnotation == null) {
            return pjp.proceed()
        }

        var rateLimitName = environment.resolvePlaceholders(finalAnnotation.name).takeIf {
            StrUtil.isNotBlank(it) && !it.contains("\${")
        } ?: "$className-$methodName"

        if (classAnnotation != null && methodAnnotation == null) {
            rateLimitName = className
        }

        if (StrUtil.isEmpty(rateLimitName) || rateLimitName.contains("\${")) {
            rateLimitName = "$className-$methodName"
        }

        val newRateLimiter: BHRateLimiter = this.getRateLimiter(rateLimitName, finalAnnotation)
        val success: Boolean = newRateLimiter.tryAcquire()

        if (success) {
            return pjp.proceed()
        }

        log.error("around|访问接口过于频繁|{}|{}", className, methodName)
        throw BusinessException("访问过于频繁，请稍后重试")
    }

    private fun getRateLimiter(rateLimitName: String, rateLimiter: AnotherDomainRateLimiter): BHRateLimiter {
        var bhRateLimiter = BH_RATE_LIMITER_MAP[rateLimitName]
        if (bhRateLimiter == null) {
            val finalRateLimitName = rateLimitName.intern()
            synchronized(finalRateLimitName) {
                bhRateLimiter = BH_RATE_LIMITER_MAP[rateLimitName]
                if (bhRateLimiter == null) {
                    val permitsPerSecond: Long = if (rateLimiter.permitsPerSecond <= 0)
                        defaultPermitsPerSecond
                    else
                        rateLimiter.permitsPerSecond

                    val timeout = if (rateLimiter.timeout <= 0)
                        defaultTimeout
                    else
                        rateLimiter.timeout

                    val timeoutUnit = rateLimiter.timeoutUnit
                    bhRateLimiter = BHRateLimiter(RateLimiter.create(permitsPerSecond.toDouble()), timeout, timeoutUnit)
                    BH_RATE_LIMITER_MAP.putIfAbsent(rateLimitName, bhRateLimiter ?: throw BusinessException("获取限流器失败"))
                }
            }
        }
        return bhRateLimiter!!
    }

    override fun setEnvironment(environment: Environment) {
        this.environment = environment
    }
}