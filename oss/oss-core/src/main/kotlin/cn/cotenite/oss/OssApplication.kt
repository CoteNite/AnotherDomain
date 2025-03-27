package cn.cotenite.oss

import cn.cotenite.oss.config.MinioProperties
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/25 12:25
 */
@SpringBootApplication
class OssApplication
fun main(args: Array<String>) {
    runApplication<OssApplication>(*args)
}