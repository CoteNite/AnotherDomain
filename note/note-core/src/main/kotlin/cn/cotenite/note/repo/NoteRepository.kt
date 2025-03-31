package cn.cotenite.note.repo

import cn.cotenite.filter.LoginUserContextHolder
import cn.cotenite.note.models.domain.*
import cn.cotenite.note.models.dto.NoteCreateInput
import cn.cotenite.note.models.dto.NoteUserUpdateInput
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.and
import org.babyfish.jimmer.sql.kt.ast.expression.eq
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

    fun updateNoteByUserIdAnCreatorId(noteUserUpdateInput: NoteUserUpdateInput){
        sqlClient.createUpdate(Note::class){
            if (noteUserUpdateInput.top!=null){
                set(table.top, noteUserUpdateInput.top)
            }
            if (noteUserUpdateInput.visible!=null){
                set(table.visible, noteUserUpdateInput.visible)
            }
            where(
                and(
                    table.id eq noteUserUpdateInput.id,
                    table.creatorId eq LoginUserContextHolder.getUserId()
                )
            )

        }
    }



}