package cn.cotenite.note.models.domain

import cn.cotenite.auth.commons.utils.SnowflakeIdGenerator
import cn.cotenite.note.common.enums.*
import org.babyfish.jimmer.sql.*
import java.time.LocalTime

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 02:38
 */
@Entity
interface Note {

    @Id
    @GeneratedValue(generatorType = SnowflakeIdGenerator::class)
    val id:Long

    val creatorId:Long

    val top:Top

    val type: Type

    val visible:Visible

    val status:Status

    val createTime:LocalTime

    val updateTime:LocalTime

    @Column(name = "`delete`")
    @Default("UNDELETE")
    @LogicalDeleted("DELETED")
    val delete:Delete

}