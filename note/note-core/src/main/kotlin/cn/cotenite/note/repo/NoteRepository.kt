package cn.cotenite.note.repo

import cn.cotenite.note.models.dto.NoteCreateInput
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.stereotype.Repository

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 05:48
 */
@Repository
class NoteRepository(
    private val sqlClient: KSqlClient
){
    fun createNote(noteCreateInput: NoteCreateInput):Long{
        return sqlClient.save(
            noteCreateInput,
            SaveMode.INSERT_ONLY
        )
            .modifiedEntity
            .id
    }

}