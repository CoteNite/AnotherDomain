package cn.cotenite.user

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/25 13:20
 */

@SpringBootApplication
@EnableDubbo
class UserApplication

fun main(args: Array<String>) {
    runApplication<UserApplication>(*args)
}