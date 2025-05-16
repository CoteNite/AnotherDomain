package cn.cotenite.note.command

import cn.cotenite.asp.Slf4j
import cn.cotenite.asp.Slf4j.Companion.log
import cn.cotenite.enums.getEntityByCode
import cn.cotenite.note.common.constants.MQConstants
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
import cn.cotenite.note.producer.CacheDeleteProducer
import cn.cotenite.note.repository.cache.NoteAggCacheRepository
import cn.cotenite.note.repository.cassandra.NoteContentRepository
import cn.cotenite.note.repository.database.NoteRepository
import cn.cotenite.utils.UserIdContextHolder
import org.apache.rocketmq.client.producer.SendCallback
import org.apache.rocketmq.client.producer.SendResult
import org.apache.rocketmq.spring.core.RocketMQTemplate
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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

@Slf4j
@Service
class NoteCommandImpl(
    private val noteRepository: NoteRepository,
    private val noteAggCacheRepository: NoteAggCacheRepository,
    private val noteContentRepository: NoteContentRepository,
    private val cacheDeleteProducer: CacheDeleteProducer
): NoteCommand {

    @Transactional(rollbackFor = [Exception::class])
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

    @Transactional(rollbackFor = [Exception::class])
    override fun updateImageNote(request: ImageNoteUpdateRequest) {
        val contentUuidForNoteUpdate= noteContentRepository.updateNoteContent(request.contentUuid,request.content)
        noteAggCacheRepository.deleteCache(request.id)
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
        cacheDeleteProducer.deleteNoteCache(request.id.toString())
        noteRepository.updateImageNote(input)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun updateVideoNote(request: VideoNoteUpdateRequest) {
        val contentUuidForNoteUpdate= noteContentRepository.updateNoteContent(request.contentUuid,request.content)
        noteAggCacheRepository.deleteCache(request.id)
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
        cacheDeleteProducer.deleteNoteCache(request.id.toString())
        noteRepository.updateVideoNote(input)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteNote(id: Long) {
        val userId = UserIdContextHolder.getId()
        noteAggCacheRepository.deleteCache(id)
        val contentId = noteRepository.deleteNoteWithCreatorId(id, userId)
        noteContentRepository.deleteById(UUID.fromString(contentId))
        cacheDeleteProducer.deleteNoteCache(id.toString())
    }


}

