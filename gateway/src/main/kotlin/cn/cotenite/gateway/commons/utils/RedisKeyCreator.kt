package cn.cotenite.gateway.commons.utils

import cn.cotenite.utils.RedisKeyBase

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/17 04:05
 */
object RedisKeyCreator : RedisKeyBase() {

    private val PREFIX = BASE_KEY+"auth:"

    fun userPermissionHashKey(userId:Long):String {
        return PREFIX +"userPermission:${userId}"
    }
}