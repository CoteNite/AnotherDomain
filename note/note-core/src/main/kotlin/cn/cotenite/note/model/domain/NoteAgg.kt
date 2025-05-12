package cn.cotenite.note.model.domain

import cn.cotenite.note.model.entity.Note
import cn.cotenite.note.model.entity.NoteContent

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/13 05:36
 */
data class NoteAgg(
    val note: Note,
    val noteContent: NoteContent
){


}