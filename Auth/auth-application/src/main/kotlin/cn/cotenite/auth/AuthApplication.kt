package cn.cotenite.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/15 03:35
 */
@SpringBootApplication
class AuthApplication

fun main(args: Array<String>) {
    runApplication<AuthApplication>(*args)
}