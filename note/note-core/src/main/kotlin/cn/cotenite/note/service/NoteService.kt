package cn.cotenite.note.service

import cn.cotenite.api.command.NoteContentCommand
import cn.cotenite.api.dto.UpdateNoteContentReqDTO
import cn.cotenite.filter.LoginUserContextHolder
import cn.cotenite.note.command.NoteCommand
import cn.cotenite.note.command.NoteDetailCommand
import cn.cotenite.note.common.enums.Top
import cn.cotenite.note.common.enums.Type
import cn.cotenite.note.common.enums.Visible
import cn.cotenite.note.models.dto.*
import io.seata.spring.annotation.GlobalTransactional
import org.apache.dubbo.config.annotation.DubboReference
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 06:24
 */
interface NoteService {
    fun createNote(createOrUpdateNoteDTO: CreateOrUpdateNoteDTO)
    fun updateVideoNoteDetail(videoNoteDetailUpdateInput: VideoNoteDetailUpdateInput)
    fun updateTextNoteDetail(textNoteDetailUpdateInput: TextNoteDetailUpdateInput)
}

@Service
class NoteServiceImpl(
    private val noteDetailCommand: NoteDetailCommand,
    private val noteCommand: NoteCommand,
    @DubboReference(interfaceClass = NoteContentCommand::class,version = "1.0")
    private val noteContentCommand: NoteContentCommand
):NoteService{


    @Transactional(rollbackFor = [Exception::class])
    @GlobalTransactional(rollbackFor = [Exception::class])
    override fun createNote(createOrUpdateNoteDTO: CreateOrUpdateNoteDTO) {
        val noteCreateInput = NoteCreateInput(
            creatorId = LoginUserContextHolder.getUserId(),
            type = Type.getTypeByCode(createOrUpdateNoteDTO.type),
            visible = Visible.getVisibleByCode(createOrUpdateNoteDTO.visible),
            top = Top.getTopByCode(createOrUpdateNoteDTO.top)
        )
        val noteId = noteCommand.createNote(noteCreateInput)
        val noteContentId = noteContentCommand.addNoteContent(createOrUpdateNoteDTO.content).toString()
        val noteDetailCreateInput = NoteDetailCreateInput(
            id = noteId,
            contentUUID = noteContentId,
            title = createOrUpdateNoteDTO.title,
            imgUris = createOrUpdateNoteDTO.imgUris,
            videoUri = createOrUpdateNoteDTO.videoUri
        )
        noteDetailCommand.createNoteDetail(noteDetailCreateInput)
    }

    override fun updateVideoNoteDetail(videoNoteDetailUpdateInput: VideoNoteDetailUpdateInput) {
        val dto = UpdateNoteContentReqDTO(
            UUID.fromString(videoNoteDetailUpdateInput.contentUUID),
            videoNoteDetailUpdateInput.content
        )
        noteContentCommand.updateNoteContent(dto)
        noteDetailCommand.updateVideoNoteDetail(videoNoteDetailUpdateInput)
    }

    override fun updateTextNoteDetail(textNoteDetailUpdateInput: TextNoteDetailUpdateInput) {
        val dto = UpdateNoteContentReqDTO(
            UUID.fromString(textNoteDetailUpdateInput.contentUUID),
            textNoteDetailUpdateInput.content
        )
        noteContentCommand.updateNoteContent(dto)
        noteDetailCommand.updateTextNoteDetail(textNoteDetailUpdateInput)
    }


}