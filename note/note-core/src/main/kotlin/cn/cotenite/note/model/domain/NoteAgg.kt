package cn.cotenite.note.model.domain

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import cn.cotenite.note.common.enums.Status
import cn.cotenite.note.common.enums.Visible
import cn.cotenite.note.model.entity.Note
import cn.cotenite.note.model.entity.NoteContent
import cn.cotenite.note.model.entity.dto.NoteDetailView
import cn.cotenite.utils.UserIdContextHolder

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/13 05:36
 */
data class NoteAgg(
    val note: Note,
    var noteContent: String?
){

    fun createNoteDetailView(): NoteDetailView {
        val creatorId =UserIdContextHolder.getId()
        if (note.creatorId == creatorId||(note.status==Status.REVIEWED||note.visible==Visible.ALL_VISIBLE)){
            return NoteDetailView(
                id=note.id,
                creatorId = note.creatorId,
                top = note.top,
                type = note.type,
                status = note.status,
                visible = note.visible,
                tag = note.tag,
                title = note.title,
                content = noteContent,
                imgUris = note.imgUris,
                videoUri = note.videoUri,
                updateTime = note.updateTime
            )
        }
        throw BusinessException(Errors.PARAM_VALIDATION_ERROR)
    }

}