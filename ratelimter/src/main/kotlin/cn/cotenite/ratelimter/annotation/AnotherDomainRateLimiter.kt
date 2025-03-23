package cn.cotenite.ratelimter.annotation

import java.lang.annotation.Inherited
import java.util.concurrent.TimeUnit

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/23 05:19
 */
@Inherited
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD,AnnotationTarget.CLASS)
annotation class AnotherDomainRateLimiter(
    val name: String = "value",
    val permitsPerSecond: Long = 0,
    val timeout: Long = 0,
    val timeoutUnit: TimeUnit = TimeUnit.SECONDS
)