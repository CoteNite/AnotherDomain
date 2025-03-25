package cn.cotenite.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/15 03:35
 */
@SpringBootApplication
@ComponentScan("cn.cotenite.*")
class AuthApplication

fun main(args: Array<String>) {
    runApplication<AuthApplication>(*args)
}