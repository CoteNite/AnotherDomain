package cn.cotenite.note.repository.cassandra

import cn.cotenite.note.model.entity.NoteContent
import org.springframework.data.cassandra.repository.CassandraRepository
import java.util.*

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/13 03:25
 */
interface NoteContentRepository : CassandraRepository<NoteContent, UUID>{

    fun updateNoteContent(contentUuid:String?,content:String?):String?{
        var updateId:String?=contentUuid
        content?.let {
            val uuid = if (contentUuid == null) {
                UUID.randomUUID()
            } else {
                UUID.fromString(contentUuid)
            }
            this.save(NoteContent(uuid, it))
            updateId = uuid.toString()
        }

        return updateId
    }


}