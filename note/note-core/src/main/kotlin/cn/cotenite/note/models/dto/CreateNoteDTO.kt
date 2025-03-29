package cn.cotenite.note.models.dto

import cn.cotenite.note.common.enums.Status
import cn.cotenite.note.common.enums.Top
import cn.cotenite.note.common.enums.Type
import cn.cotenite.note.common.enums.Visible

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 06:05
 */
data class CreateNoteDTO(

    val top: Int,

    val type: Int,

    val visible: Int,

    val title: String,

    val content:String?,

    val imgUris:String?,

    val videoUri:String?
)