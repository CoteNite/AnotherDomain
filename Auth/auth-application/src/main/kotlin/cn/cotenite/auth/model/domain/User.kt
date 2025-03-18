package cn.cotenite.auth.model.domain

import cn.cotenite.auth.commons.enums.Delete
import cn.cotenite.expection.BusinessException
import org.babyfish.jimmer.sql.*
import org.springframework.security.crypto.bcrypt.BCrypt
import java.time.LocalTime

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/15 05:44
 */
@Entity
interface User{

    @Id
    val email:String

    val userId:Long

    val password:String

    val userNumber:String

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