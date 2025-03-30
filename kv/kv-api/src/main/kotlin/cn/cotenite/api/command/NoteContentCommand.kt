package cn.cotenite.api.command

import cn.cotenite.api.dto.UpdateNoteContentReqDTO
import java.util.*

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/29 20:25
 */
interface NoteContentCommand {

    fun addNoteContent(content:String?): UUID

    fun deleteNoteContent(noteId: UUID)

    fun updateNoteContent(reqDTO: UpdateNoteContentReqDTO)

}