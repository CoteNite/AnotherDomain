package cn.cotenite.auth.model.po

import org.babyfish.jimmer.sql.*
import java.time.LocalTime

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/19 08:52
 */
@Entity
interface Role {

    @Id
    val id:Long

    val roleName:String

    @ManyToMany
    @JoinTable(name = "role_permission")
    val permissions:List<Permission>

    val createTime: LocalTime

    val updateTime: LocalTime
}