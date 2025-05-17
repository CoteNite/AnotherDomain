package cn.cotenite.model.domain

import cn.cotenite.note.common.enums.Delete
import cn.cotenite.note.common.utils.SnowflakeIdGenerator
import org.babyfish.jimmer.sql.*
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/17 03:32
 */
@Entity
interface Follow {

    @Id
    @GeneratedValue(generatorType = SnowflakeIdGenerator::class)
    val id:Long

    val userId:Long

    val followingUserId:Long

    val createTime: LocalDateTime

    val updateTime:LocalDateTime

    @Column(name = "`delete`")
    @Default("UNDELETE")
    @LogicalDeleted("DELETED")
    val delete: Delete

}