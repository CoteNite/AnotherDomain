package cn.cotenite.note.repository.database

import cn.cotenite.note.model.entity.*
import cn.cotenite.note.model.entity.dto.DeleteNoteView
import cn.cotenite.note.model.entity.dto.ImageNoteUpdateInput
import cn.cotenite.note.model.entity.dto.NoteAddInput
import cn.cotenite.note.model.entity.dto.VideoNoteUpdateInput
import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Repository

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/13 05:55
 */
@Repository
class NoteRepository(
    private val sqlClient: KSqlClient,
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

    fun selectOneById(id: Long): DeleteNoteView {
        return sqlClient.createQuery(Note::class){
            where(table.id eq id)
            select(
                table.fetch(DeleteNoteView::class)
            )
        }.fetchOne()
    }

    fun deleteNote(id: Long) {
        sqlClient.deleteById(Note::class,id)
    }

}