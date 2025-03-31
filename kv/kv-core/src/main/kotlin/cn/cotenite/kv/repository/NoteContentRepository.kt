package cn.cotenite.kv.repository

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import cn.cotenite.kv.model.dataobject.NoteContent
import org.springframework.data.cassandra.repository.CassandraRepository
import java.util.*

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/29 20:24
 */
interface NoteContentRepository : CassandraRepository<NoteContent, UUID> {

    fun updateNoteContentById(noteId: UUID, content: String?){
        checkNoteContentExist(noteId)
        this.
        save(NoteContent(noteId,content))
    }

    fun deleteNoteContentById(noteId: UUID){
        checkNoteContentExist(noteId)
        deleteById(noteId)
    }

    fun findContentById(id: UUID): String?{
        val noteContent = this.findById(id)
        if (noteContent.isEmpty) {
            return null
        }
        return noteContent.get().content
    }

    fun existsNoteContentsById(id: UUID): Boolean

    private fun checkNoteContentExist(noteId: UUID) {
        if (existsNoteContentsById(noteId)) {
            throw BusinessException(Errors.PARAM_VALIDATION_ERROR)
        }
    }
}

