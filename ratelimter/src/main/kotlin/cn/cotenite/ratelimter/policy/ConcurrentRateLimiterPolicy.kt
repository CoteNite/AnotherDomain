package cn.cotenite.ratelimter.policy

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadPoolExecutor

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/23 06:58
 */
class ConcurrentRateLimiterPolicy: RejectedExecutionHandler{
    override fun rejectedExecution(r: Runnable?, executor: ThreadPoolExecutor?) {
        throw BusinessException(Errors.RETRY_LATER.message)
    }
}