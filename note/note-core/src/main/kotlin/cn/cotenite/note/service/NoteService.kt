package cn.cotenite.note.service

import cn.cotenite.api.command.NoteContentCommand
import cn.cotenite.filter.LoginUserContextHolder
import cn.cotenite.note.command.NoteCommand
import cn.cotenite.note.command.NoteDetailCommand
import cn.cotenite.note.common.enums.Top
import cn.cotenite.note.common.enums.Type
import cn.cotenite.note.common.enums.Type.*
import cn.cotenite.note.common.enums.Visible
import cn.cotenite.note.models.dto.CreateNoteDTO
import cn.cotenite.note.models.dto.NoteCreateInput
import cn.cotenite.note.models.dto.NoteDetailCreateInput
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

    fun createNote(createNoteDTO: CreateNoteDTO)
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
    override fun createNote(createNoteDTO: CreateNoteDTO) {
        val noteCreateInput = NoteCreateInput(
            creatorId = LoginUserContextHolder.getUserId(),
            type = Type.getTypeByCode(createNoteDTO.type),
            visible = Visible.getVisibleByCode(createNoteDTO.visible),
            top = Top.getTopByCode(createNoteDTO.top)
        )
        val noteId = noteCommand.createNote(noteCreateInput)
        val noteContentId = noteContentCommand.addNoteContent(createNoteDTO.content).toString()
        val noteDetailCreateInput = NoteDetailCreateInput(
            id = noteId,
            contentUUID = noteContentId,
            title = createNoteDTO.title,
            imgUris = createNoteDTO.imgUris,
            videoUri = createNoteDTO.videoUri
        )
        noteDetailCommand.createNoteDetail(noteDetailCreateInput)
    }


}