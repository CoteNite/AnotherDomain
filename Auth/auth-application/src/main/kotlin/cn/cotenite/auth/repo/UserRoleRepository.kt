package cn.cotenite.auth.repo

import cn.cotenite.auth.model.po.dto.UserRoleSaveInput
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.stereotype.Repository

/**
 * @Author  RichardYoung
 * @Description  
 * @Date  2025/3/20 14:56
*/
@Repository
class UserRoleRepository(
    private val sqlClient: KSqlClient
){
    fun saveUserRole4Register(input: UserRoleSaveInput) {
        sqlClient.save(
            input,
            SaveMode.INSERT_ONLY
        )
    }
}