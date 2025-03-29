package cn.cotenite.kv.command

import cn.cotenite.api.command.NoteContentCommand
import cn.cotenite.api.dto.AddNoteContentReqDTO
import cn.cotenite.kv.model.dataobject.NoteContent
import cn.cotenite.kv.repository.NoteContentRepository
import org.apache.dubbo.config.annotation.DubboService
import org.springframework.web.bind.annotation.CrossOrigin
import java.util.*

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/29 20:29
 */
@CrossOrigin("*")
@DubboService(version = "1.0")
class NoteContentCommandImpl(
    private val noteContentRepository: NoteContentRepository
): NoteContentCommand {
    override fun addNoteContent(reqDTO: AddNoteContentReqDTO) {
        val noteContent = NoteContent(reqDTO.noteId, reqDTO.content)
        noteContentRepository.insert(noteContent)
    }

    override fun deleteNoteContent(noteId: UUID) {
        noteContentRepository.deleteNoteContentById(noteId)
    }

    override fun updateNoteContent(reqDTO: AddNoteContentReqDTO) {
        noteContentRepository.updateNoteContentById(reqDTO.noteId, reqDTO.content)
    }
}