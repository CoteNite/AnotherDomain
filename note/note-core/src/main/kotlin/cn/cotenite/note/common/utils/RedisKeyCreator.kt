package cn.cotenite.note.common.utils

import cn.cotenite.utils.RedisKeyBase

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/17 04:05
 */
object RedisKeyCreator : RedisKeyBase() {

    private val PREFIX = BASE_KEY+"note:"

    fun buildNoteDetailKey(noteId:Long):String {
        return PREFIX +"detail:${noteId}"
    }

}