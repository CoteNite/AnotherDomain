package cn.cotenite.note.repo

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import cn.cotenite.note.common.utils.RedisKeyCreator
import cn.cotenite.note.models.dto.*
import cn.cotenite.note.models.read.*
import cn.hutool.core.util.RandomUtil
import org.babyfish.jimmer.Page
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.boot.autoconfigure.cache.CacheProperties
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Repository
import java.time.Duration
import java.util.concurrent.TimeUnit


/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 05:56
 */
@Repository
class NoteDetailRepository(
    private val sqlClient: KSqlClient,
    private val redissonClient: RedissonClient,
    private val taskExecutor: ThreadPoolTaskExecutor
){

    companion object{
        private val LOCAL_CACHE= CacheProperties.Caffeine.newBuilder()
            .initialCapacity(10000)
            .maximumSize(10000)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build<Long,NoteDetailTextInfo>()
    }

    fun createNoteDetail(noteDetailCreateInput: NoteDetailCreateInput) {
        sqlClient.save(
            noteDetailCreateInput,
            SaveMode.INSERT_ONLY
        )
    }

    fun updateTextNoteDetail(textNoteDetailUpdateInput: TextNoteDetailUpdateInput) {
        sqlClient.createUpdate(NoteDetail::class){
            if (textNoteDetailUpdateInput.contentUUID != null){
                set(table.contentUUID, textNoteDetailUpdateInput.contentUUID)
            }
            if (textNoteDetailUpdateInput.title != null){
                set(table.title, textNoteDetailUpdateInput.title)
            }
            if (textNoteDetailUpdateInput.imgUris != null){
                set(table.title, textNoteDetailUpdateInput.imgUris)
            }
            where(
                table.id eq textNoteDetailUpdateInput.id,
                table.creatorId eq textNoteDetailUpdateInput.creatorId
            )
        }
    }

    fun updateVideoNoteDetail(videoNoteDetailUpdateInput: VideoNoteDetailUpdateInput) {
        sqlClient.createUpdate(NoteDetail::class){
            if (videoNoteDetailUpdateInput.contentUUID != null){
                set(table.contentUUID, videoNoteDetailUpdateInput.contentUUID)
            }
            if (videoNoteDetailUpdateInput.title != null){
                set(table.title, videoNoteDetailUpdateInput.title)
            }
            if (videoNoteDetailUpdateInput.videoUri != null){
                set(table.title, videoNoteDetailUpdateInput.videoUri)
            }
            where(
                table.id eq videoNoteDetailUpdateInput.id,
                table.creatorId eq videoNoteDetailUpdateInput.creatorId
            )
        }
    }

    fun findTextNoteDetailInfoById(noteId: Long): NoteDetailTextInfo {

        val key = RedisKeyCreator.buildNoteDetailKey(noteId)

        var noteDetailTextInfo:NoteDetailTextInfo?

        val bucket = redissonClient.getBucket<NoteDetailTextInfo>(key)

        noteDetailTextInfo=LOCAL_CACHE.getIfPresent(noteId)

        if (noteDetailTextInfo!=null){
            return noteDetailTextInfo
        }

        if (bucket.isExists) {
            return bucket.get()
        }

        try {
            noteDetailTextInfo=sqlClient.createQuery(NoteDetail::class) {
                where(
                    table.id eq noteId
                )
                select(
                    table.fetch(NoteDetailTextInfo::class)
                )
            }.fetchOne()
        }catch (e:Exception){
            throw BusinessException(Errors.PARAM_VALIDATION_ERROR)
        }

        taskExecutor.execute {
            LOCAL_CACHE.put(noteId,noteDetailTextInfo)
            bucket.set(noteDetailTextInfo)
            bucket.expire(Duration.ofSeconds(60+RandomUtil.randomLong(60)))
        }

        return noteDetailTextInfo
    }

    fun findVideoNoteDetailInfoById(noteId:Long): NoteDetailVideoInfo {
        return sqlClient.createQuery(NoteDetail::class) {
            where(
                table.id eq noteId
            )
            select(
                table.fetch(NoteDetailVideoInfo::class)
            )
        }.fetchOne()
    }

    fun findNoteDetailViewList(pageIndex:Int,pageSize:Int): Page<NoteViewItem> {
        return sqlClient.createQuery(NoteDetail::class) {
            orderBy(table.updateTime.desc())
            select(table.fetch(NoteViewItem::class))
        }.fetchPage(pageIndex, pageSize)
    }
}