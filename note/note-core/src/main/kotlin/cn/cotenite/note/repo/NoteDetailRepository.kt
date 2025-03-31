package cn.cotenite.note.repo

import cn.cotenite.note.models.dto.NoteDetailCreateInput
import cn.cotenite.note.models.dto.TextNoteDetailUpdateInput
import cn.cotenite.note.models.dto.VideoNoteDetailUpdateInput
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.stereotype.Repository

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
        sqlClient.save(
            textNoteDetailUpdateInput,
            SaveMode.UPDATE_ONLY
        )
    }

    fun updateVideoNoteDetail(videoNoteDetailUpdateInput: VideoNoteDetailUpdateInput) {
        sqlClient.save(
            videoNoteDetailUpdateInput,
            SaveMode.UPDATE_ONLY
        )
    }
}