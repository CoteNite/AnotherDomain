package cn.cotenite.note.repo

import cn.cotenite.note.models.dto.*
import cn.cotenite.note.models.read.*
import org.babyfish.jimmer.Page
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.ast.tuple.Tuple5
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.desc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Repository
import java.time.LocalTime

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 05:56
 */
@Repository
class NoteDetailRepository(
    private val sqlClient: KSqlClient
){
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
        return sqlClient.createQuery(NoteDetail::class) {
            where(
                table.id eq noteId
            )
            select(
                table.fetch(NoteDetailTextInfo::class)
            )
        }.fetchOne()
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