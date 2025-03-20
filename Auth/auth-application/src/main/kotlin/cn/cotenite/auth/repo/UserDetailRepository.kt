package cn.cotenite.auth.repo

import cn.cotenite.auth.model.domain.dto.dto.UserDetailSaveInput
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

    fun insertUserDetail(input: UserDetailSaveInput){
        sqlClient
            .save(
                input,
                SaveMode.INSERT_ONLY
            )

    }




}