package cn.cotenite.kv

import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/29 19:20
 */
@SpringBootApplication
class KvApplication

fun main(args: Array<String>) {
    org.springframework.boot.runApplication<KvApplication>(*args)
}