package cn.cotenite.note.repository.database

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import cn.cotenite.note.model.entity.*
import cn.cotenite.note.model.entity.dto.ImageNoteUpdateInput
import cn.cotenite.note.model.entity.dto.NoteAddInput
import cn.cotenite.note.model.entity.dto.VideoNoteUpdateInput
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Repository

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/13 05:55
 */
@Repository
class NoteRepository(
    private val sqlClient: KSqlClient,
    private val taskExecutor: ThreadPoolTaskExecutor
){

    fun insertNote(input: NoteAddInput) {
        sqlClient.save(
            input,
            SaveMode.INSERT_ONLY
        )
    }

    fun updateImageNote(input: ImageNoteUpdateInput) {
        sqlClient.createUpdate(Note::class){
            set(
                table.title,
                input.title
            )
            set(
                table.top,
                input.top
            )
            set(
                table.visible,
                input.visible
            )
            set(
                table.title,
                input.title
            )
            set(
                table.tag,
                input.tag
            )
            set(
                table.imgUris,
                input.imgUris
            )
            where(
                table.id eq input.id,
                table.creatorId eq input.creatorId
            )
        }.execute()
    }

    fun updateVideoNote(input: VideoNoteUpdateInput) {
        sqlClient.createUpdate(Note::class){
            set(
                table.title,
                input.title
            )
            set(
                table.top,
                input.top
            )
            set(
                table.visible,
                input.visible
            )
            set(
                table.title,
                input.title
            )
            set(
                table.tag,
                input.tag
            )
            set(
                table.videoUri,
                input.videoUri
            )
            where(
                table.id eq input.id,
                table.creatorId eq input.creatorId
            )
        }.execute()
    }

    fun selectOneById(id: Long): Note {
        return sqlClient.createQuery(Note::class){
            where(table.id eq id)
            select(
                table
            )
        }.fetchOne()
    }

    fun deleteNoteWithCreatorId(id:Long,creatorId:Long): String{
        val contentId = sqlClient.createQuery(Note::class) {
            where(
                table.id eq id,
                table.creatorId eq creatorId
            )
            select(table.contentUuid)
        }.fetchOne()
        if (contentId!=null){
            taskExecutor.execute{
                sqlClient.createDelete(Note::class) {
                    where(table.id eq id)
                    where(table.creatorId eq creatorId)
                }.execute()
            }
            return contentId
        }
        throw BusinessException(Errors.PARAM_VALIDATION_ERROR)
    }


}