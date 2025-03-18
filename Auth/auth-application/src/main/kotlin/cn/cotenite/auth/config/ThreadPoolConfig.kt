package cn.cotenite.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/19 05:07
 */
@Configuration
class ThreadPoolConfig {

    @Bean
    fun taskExecutor():ThreadPoolTaskExecutor{
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 10
        executor.maxPoolSize = 50
        executor.queueCapacity = 200
        executor.keepAliveSeconds = 30
        executor.setThreadNamePrefix("Auth-")

        executor.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())
        executor.setWaitForTasksToCompleteOnShutdown(true)
        executor.setAwaitTerminationSeconds(60)

        executor.initialize()

        return executor
    }
}