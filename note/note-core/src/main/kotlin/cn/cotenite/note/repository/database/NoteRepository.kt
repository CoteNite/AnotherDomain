package cn.cotenite.note.repository.database

import cn.cotenite.note.model.entity.dto.NoteAddInput
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
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

}