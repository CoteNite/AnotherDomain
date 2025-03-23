package cn.cotenite.ratelimter.annotation

import java.lang.annotation.Inherited
import java.util.concurrent.TimeUnit

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/23 06:55
 */
@Inherited
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD,AnnotationTarget.CLASS)
annotation class ConcurrentRateLimiter(
    val name: String,
    val corePoolSize: Int = 1,
    val maximumPoolSize: Int = 1,
    val queueCapacity: Int = 1,
    val keepAliveTime:Long=1,
    val keepAliveTimeUnit: TimeUnit = TimeUnit.SECONDS,
    val timeout: Long = 1,
    val timeoutTimeUnit: TimeUnit = TimeUnit.SECONDS
)
