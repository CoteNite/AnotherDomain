package cn.cotenite.api.query

import java.util.*

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/29 20:37
 */
interface NoteContentQuery {

    fun queryNoteContent(noteId: UUID):String?
}