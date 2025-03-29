package cn.cotenite.api.dto

import java.util.*


/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/29 20:26
 */
data class UpdateNoteContentReqDTO(
    val noteId: UUID,
    val content:String
)
