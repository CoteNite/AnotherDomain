package cn.cotenite.ratelimter.bean

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import java.util.concurrent.Callable
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/23 07:02
 */
class BHConcurrentRateLimiter(
    val executor: ThreadPoolExecutor,
    val timeout:Long,
    val timeoutUnit: TimeUnit
){

    fun <T> submit(task:Callable<T>):T{
        try {
            return executor.submit(task).get()
        }catch (e:Exception){
            if(e is BusinessException){
                val businessException = e as BusinessException
                throw businessException
            }
            throw BusinessException(Errors.SERVER_ERROR)
        }
    }

    fun <T> submitWithTimeout(task:Callable<T>): T {
        try {
            return executor.submit(task).get(timeout, timeoutUnit);
        } catch (e:Exception) {
            if (e is BusinessException){
                val businessException = e as BusinessException
                throw businessException;
            }
            throw BusinessException(Errors.SERVER_ERROR)
        }
    }

    fun shutdown(){
        executor.shutdown()
    }
}