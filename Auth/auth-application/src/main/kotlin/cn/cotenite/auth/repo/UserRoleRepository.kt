package cn.cotenite.auth.repo

import cn.cotenite.auth.commons.enums.RoleCommons
import cn.cotenite.auth.model.po.UserRole
import cn.cotenite.auth.model.po.dto.Save4UserUserRoleInput
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.stereotype.Repository

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/20 09:35
 */
@Repository
class UserRoleRepository(
    private val sqlClient: KSqlClient
){

    fun saveRelation4SaveUser(userId:Long){
        val save4UserUserRoleInput = Save4UserUserRoleInput(userId, RoleCommons.USER.roleId)
        sqlClient.save(save4UserUserRoleInput)
    }


}