package cn.cotenite.note.models.read

import cn.cotenite.note.common.enums.Delete
import org.babyfish.jimmer.sql.*
import java.time.LocalTime

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 04:18
 */
@Entity
interface NoteDetail {

    @Id
    val id:Long

    val title:String

    val contentUUID:String

    val imgUris:String

    val videoUri:String

    val createTime:LocalTime

    val updateTime:LocalTime

    @Column(name = "`delete`")
    @Default("UNDELETE")
    @LogicalDeleted("DELETED")
    val delete: Delete
}