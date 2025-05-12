package cn.cotenite.note.command

import cn.cotenite.enums.getEntityByCode
import cn.cotenite.note.common.enums.Top
import cn.cotenite.note.common.enums.Type
import cn.cotenite.note.common.enums.Visible
import cn.cotenite.note.model.entity.NoteContent
import cn.cotenite.note.model.entity.dto.NoteAddInput
import cn.cotenite.note.model.request.NoteAddRequest
import cn.cotenite.note.repository.cassandra.NoteContentRepository
import cn.cotenite.note.repository.database.NoteRepository
import org.springframework.stereotype.Service
import java.util.Random
import java.util.UUID

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/13 05:58
 */
interface NoteCommand {

    fun addNote(request: NoteAddRequest)

}

@Service
class NoteCommandImpl(
    private val noteRepository: NoteRepository,
    private val noteContentRepository: NoteContentRepository
): NoteCommand {

    override fun addNote(request: NoteAddRequest) {
        var randomUUID:UUID?=null
        if (request.content!=null){
            randomUUID=UUID.randomUUID()
            val noteContent = NoteContent(randomUUID, request.content)
            noteContentRepository.save(noteContent)
        }
        val input=NoteAddInput(
            creatorId = request.creatorId,
            top = getEntityByCode<Top>(request.top),
            type = getEntityByCode<Type>(request.type),
            visible = getEntityByCode<Visible>(request.visible),
            title = request.title,
            tag = request.tag,
            contentUuid = randomUUID?.toString(),
            imgUris = request.imgUris,
            videoUri = request.videoUri
        )
        noteRepository.insertNote(input)
    }



}

