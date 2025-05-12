package cn.cotenite.note.model.entity

import cn.cotenite.note.common.enums.*
import cn.cotenite.note.common.utils.SnowflakeIdGenerator
import org.babyfish.jimmer.sql.*
import java.time.LocalTime

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/13 05:37
 */
@Entity
interface Note {

    @Id
    @GeneratedValue(generatorType = SnowflakeIdGenerator::class)
    val id:Long

    val creatorId:Long

    val top:Top

    val type:Type

    val status:Status

    val visible:Visible

    val tag:String?

    val title:String

    val contentUuid:String?

    val imgUris:String?

    val videoUri:String?

    val createTime: LocalTime

    val updateTime:LocalTime

    @Column(name = "`delete`")
    @Default("UNDELETE")
    @LogicalDeleted("DELETED")
    val delete: Delete
}