package cn.cotenite.ratelimter.bean

import com.google.common.util.concurrent.RateLimiter
import java.util.concurrent.TimeUnit

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/23 05:23
 */
data class BHRateLimiter(
    val rateLimiter:RateLimiter,
    val timeout:Long,
    val timeUnit: TimeUnit
){
    fun tryAcquire():Boolean{
        return rateLimiter.tryAcquire()
    }

    fun tryAcquireWithTimeout():Boolean{
        return rateLimiter.tryAcquire(timeout, timeUnit);
    }

    fun tryAcquire(permits:Int):Boolean{
        return rateLimiter.tryAcquire(permits)
    }

    fun tryAcquireWithTimeout(permits:Int):Boolean{
        return rateLimiter.tryAcquire(permits, timeout, timeUnit);
    }
}