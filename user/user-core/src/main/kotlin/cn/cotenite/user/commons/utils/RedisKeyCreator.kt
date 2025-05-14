package cn.cotenite.user.commons.utils

import cn.cotenite.utils.RedisKeyBase

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/15 06:37
 */
object RedisKeyCreator : RedisKeyBase() {

    private val PREFIX = "$BASE_KEY:userDetail:"

    fun buildNoteDetailSimpleViewKey(id:Long): String {
        return "${PREFIX}simpleView:${id}"
    }



}