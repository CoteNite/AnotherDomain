package cn.cotenite.user.repository

import cn.cotenite.user.model.domain.dto.UserDetailSaveInput
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.stereotype.Repository

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/19 07:19
 */
@Repository
class UserDetailRepository(
    private val sqlClient:KSqlClient
){

    fun saveUserDetail4Create(userDetailSaveInput: UserDetailSaveInput) {
        sqlClient.save(
            userDetailSaveInput,
            SaveMode.INSERT_ONLY
        )
    }





}