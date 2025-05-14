package cn.cotenite.note.command

import cn.cotenite.enums.Errors
import cn.cotenite.enums.getEntityByCode
import cn.cotenite.expection.BusinessException
import cn.cotenite.note.common.enums.Top
import cn.cotenite.note.common.enums.Type
import cn.cotenite.note.common.enums.Visible
import cn.cotenite.note.model.entity.NoteContent
import cn.cotenite.note.model.entity.dto.ImageNoteUpdateInput
import cn.cotenite.note.model.entity.dto.NoteAddInput
import cn.cotenite.note.model.entity.dto.VideoNoteUpdateInput
import cn.cotenite.note.model.request.ImageNoteUpdateRequest
import cn.cotenite.note.model.request.NoteAddRequest
import cn.cotenite.note.model.request.VideoNoteUpdateRequest
import cn.cotenite.note.repository.cassandra.NoteContentRepository
import cn.cotenite.note.repository.database.NoteRepository
import cn.cotenite.utils.UserIdContextHolder
import org.springframework.stereotype.Service
import java.util.UUID

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/13 05:58
 */
interface NoteCommand {
    fun addNote(request: NoteAddRequest)
    fun updateImageNote(request: ImageNoteUpdateRequest)
    fun updateVideoNote(request: VideoNoteUpdateRequest)
    fun deleteNote(id: Long)
}

@Service
class NoteCommandImpl(
    private val noteRepository: NoteRepository,
    private val noteContentRepository: NoteContentRepository
): NoteCommand {

    override fun addNote(request: NoteAddRequest) {
        val randomUUID = request.content?.let {
            UUID.randomUUID().also { uuid ->
                noteContentRepository.save(NoteContent(uuid, it))
            }
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

    override fun updateImageNote(request: ImageNoteUpdateRequest) {
        val contentUuidForNoteUpdate= this.updateNoteContent(request.contentUuid,request.content)

        val input = ImageNoteUpdateInput(
            id = request.id,
            creatorId = UserIdContextHolder.getId(),
            title = request.title,
            tag = request.tag,
            contentUuid = contentUuidForNoteUpdate,
            imgUris = request.imgUris,
            top = getEntityByCode<Top>(request.top),
            visible = getEntityByCode<Visible>(request.visible)
        )
        noteRepository.updateImageNote(input)
    }

    override fun updateVideoNote(request: VideoNoteUpdateRequest) {
        val contentUuidForNoteUpdate= this.updateNoteContent(request.contentUuid,request.content)
        val input = VideoNoteUpdateInput(
            id = request.id,
            creatorId = UserIdContextHolder.getId(),
            title = request.title,
            tag = request.tag,
            contentUuid = contentUuidForNoteUpdate,
            videoUri = request.videoUri,
            top = getEntityByCode<Top>(request.top),
            visible = getEntityByCode<Visible>(request.visible)
        )
        noteRepository.updateVideoNote(input)
    }

    override fun deleteNote(id: Long) {
        val userId = UserIdContextHolder.getId()
        val contentId = noteRepository.deleteNoteWithCreatorId(id, userId)
        noteContentRepository.deleteById(UUID.fromString(contentId))
    }

    private fun updateNoteContent(contentUuid:String?,content:String?):String?{
        var updateId:String?=contentUuid
        content?.let {
            val uuid = if (contentUuid == null) {
                UUID.randomUUID()
            } else {
                UUID.fromString(contentUuid)
            }
            noteContentRepository.save(NoteContent(uuid, it))
            updateId = uuid.toString()
        }
        return updateId
    }

}

