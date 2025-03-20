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
    val id:Int

    val roleName:String

    @OneToMany(mappedBy = "role")
    val userRoles:List<UserRole>

    @OneToMany(mappedBy = "role")
    val permissions:List<Permission>

    val createTime: LocalTime

    val updateTime: LocalTime


}