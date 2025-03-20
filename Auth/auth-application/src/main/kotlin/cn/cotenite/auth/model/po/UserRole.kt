package cn.cotenite.auth.model.po

import cn.cotenite.auth.commons.enums.Blacked
import cn.cotenite.auth.commons.utils.SnowflakeIdGenerator
import cn.cotenite.auth.model.domain.agg.User
import org.babyfish.jimmer.sql.*
import java.time.LocalTime

/**
 * @Author  RichardYoung
 * @Description  
 * @Date  2025/3/20 14:47
*/
@Entity
interface UserRole {

    @Id
    @GeneratedValue(generatorType = SnowflakeIdGenerator::class)
    val id:Long

    @ManyToOne
    val user: User

    @ManyToOne
    val role:Role

    val black:Blacked

    val createTime: LocalTime

    val updateTime: LocalTime
}