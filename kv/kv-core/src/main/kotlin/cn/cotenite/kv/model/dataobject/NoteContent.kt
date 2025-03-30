package cn.cotenite.kv.model.dataobject

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.util.UUID

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/29 20:16
 */
@Table("note_content")
data class NoteContent(
    @PrimaryKey
    val id:UUID,
    val content:String?
){

    init {
        content?:throw BusinessException(Errors.PARAM_VALIDATION_ERROR)
    }


}
