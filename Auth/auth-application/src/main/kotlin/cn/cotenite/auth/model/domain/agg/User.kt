package cn.cotenite.auth.model.domain.agg

import cn.cotenite.auth.commons.enums.Delete
import cn.cotenite.auth.commons.utils.SnowflakeIdGenerator
import cn.cotenite.auth.model.po.Permission
import cn.cotenite.auth.model.po.Role
import cn.cotenite.expection.BusinessException
import org.babyfish.jimmer.Immutable
import org.babyfish.jimmer.sql.*
import org.springframework.security.crypto.bcrypt.BCrypt
import java.time.LocalTime
import java.util.Arrays

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/15 05:44
 */
@Entity
interface User{

    @Id
    @GeneratedValue(generatorType = SnowflakeIdGenerator::class)
    val id:Long

    @Key
    val email:String

    val userId:Long

    val password:String

    val userNumber:String

    @ManyToMany
    @JoinTable(name = "user_role")
    val roles:List<Role>


    val createTime:LocalTime

    val updateTime:LocalTime

    @Default("UNDELETE")
    @LogicalDeleted("DELETED")
    val delete: Delete

    fun verify(password: String):Long{

        val verifyPwd = BCrypt.checkpw(password,this.password)
        if (!verifyPwd){
            throw BusinessException("用户名或密码错误")
        }
        return this.userId
    }

}