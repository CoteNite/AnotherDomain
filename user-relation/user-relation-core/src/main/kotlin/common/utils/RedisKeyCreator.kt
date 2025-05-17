package cn.cotenite.note.common.utils

import cn.cotenite.utils.RedisKeyBase

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/15 03:40
 */
object RedisKeyCreator : RedisKeyBase() {


    private val PREFIX = "$BASE_KEY:user-relation:"

    fun buildFollowSetRedisKey(id:Long): String {
        return "${PREFIX}followSet:${id}"
    }




}