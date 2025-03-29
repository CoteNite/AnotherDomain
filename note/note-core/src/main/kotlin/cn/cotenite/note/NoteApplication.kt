package cn.cotenite.note

import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/29 21:32
 */
@SpringBootApplication
class NoteApplication

fun main(args: Array<String>) {
    org.springframework.boot.runApplication<NoteApplication>(*args)
}