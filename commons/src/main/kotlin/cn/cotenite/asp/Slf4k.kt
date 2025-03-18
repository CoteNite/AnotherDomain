package cn.cotenite.asp

import mu.KotlinLogging
import org.slf4j.Logger

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/17 05:37
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Slf4j{
    companion object{
        val <reified T> T.log: Logger
            inline get() = KotlinLogging.logger{T::class.java.name}
    }
}