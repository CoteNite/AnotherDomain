package cn.cotenite.auth.model.po

import cn.cotenite.auth.commons.utils.SnowflakeIdGenerator
import org.babyfish.jimmer.sql.*
import java.time.LocalTime

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/20 09:34
 */
@Entity
interface UserRole {

    @Id
    @GeneratedValue(generatorType = SnowflakeIdGenerator::class)
    val id:Long

    val userId:Long

    val roleId:Int

    val createTime:LocalTime

    val updateTime:LocalTime

}