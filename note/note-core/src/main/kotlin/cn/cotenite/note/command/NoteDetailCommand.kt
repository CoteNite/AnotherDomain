package cn.cotenite.note.command

import cn.cotenite.api.command.NoteContentCommand
import cn.cotenite.api.dto.UpdateNoteContentReqDTO
import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import cn.cotenite.note.models.dto.NoteDetailCreateInput
import cn.cotenite.note.models.dto.TextNoteDetailUpdateInput
import cn.cotenite.note.models.dto.VideoNoteDetailUpdateInput
import cn.cotenite.note.repo.NoteDetailRepository
import org.apache.dubbo.config.annotation.DubboReference
import org.springframework.stereotype.Service
import java.util.*

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 05:55
 */
interface NoteDetailCommand {
    fun createNoteDetail( noteDetailCreateInput: NoteDetailCreateInput)
    fun updateVideoNoteDetail(videoNoteDetailUpdateInput: VideoNoteDetailUpdateInput)
    fun updateTextNoteDetail(textNoteDetailUpdateInput: TextNoteDetailUpdateInput)

}

@Service
class NoteDetailCommandImpl(
    private val noteDetailRepository: NoteDetailRepository
): NoteDetailCommand {

    override fun createNoteDetail(noteDetailCreateInput: NoteDetailCreateInput) {
        noteDetailRepository.createNoteDetail(noteDetailCreateInput)
    }

    override fun updateVideoNoteDetail(videoNoteDetailUpdateInput: VideoNoteDetailUpdateInput) {
        noteDetailRepository.updateVideoNoteDetail(videoNoteDetailUpdateInput)
    }

    override fun updateTextNoteDetail(textNoteDetailUpdateInput: TextNoteDetailUpdateInput) {
        noteDetailRepository.updateTextNoteDetail(textNoteDetailUpdateInput)
    }

}
