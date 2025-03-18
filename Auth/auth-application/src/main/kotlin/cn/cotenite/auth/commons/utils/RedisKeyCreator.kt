package cn.cotenite.auth.commons.utils

import cn.cotenite.utils.RedisKeyBase

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/17 04:05
 */
object RedisKeyCreator : RedisKeyBase() {

    private val PREFIX = BASE_KEY+"auth:"

    fun registerCodeKey(email:String):String {
        return PREFIX+"register:${email}"
    }

    fun loginCodeKey(key:String):String {
        return PREFIX+"login:${key}"
    }

    fun resetPasswordCodeKey(email:String):String {
        return PREFIX+"resetPassword:${email}"
    }

}