package cn.cotenite.utils

import java.time.LocalDateTime
import java.time.ZoneOffset


/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/18 04:54
 */
object DateUtil {

    fun localDateTime2Timestamp(localDateTime: LocalDateTime): Long {
        return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
    }

}