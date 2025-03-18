package cn.cotenite.utils

import java.util.*

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/19 01:42
 */
object RandomKeyCreator {
    fun getUuidKey():String{
        return UUID.randomUUID().toString().replace("-", "")
    }
}