package cn.cotenite.auth.model.po

import cn.cotenite.auth.model.domain.agg.User
import org.babyfish.jimmer.sql.*
import java.time.LocalTime

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/19 08:55
 */
@Entity
interface Permission {

    @Id
    val id:Long

    val permissionName:String

    val createTime: LocalTime

    val updateTime: LocalTime

}