package cn.cotenite.auth.repo


import cn.cotenite.asp.Slf4j

import cn.cotenite.auth.model.domain.User
import cn.cotenite.auth.model.domain.dto.dto.ResetPasswordInput
import cn.cotenite.auth.model.domain.dto.dto.UserInput
import cn.cotenite.auth.model.domain.email
import cn.cotenite.auth.model.domain.userNumber
import cn.cotenite.expection.BusinessException
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.exception.EmptyResultException
import org.babyfish.jimmer.sql.exception.SaveException
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.or
import org.springframework.stereotype.Repository

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/16 06:27
 */
@Slf4j
@Repository
class UserRepository(
    private val sqlClient: KSqlClient
) {
    fun saveUser(input: UserInput){
        try {
            sqlClient.save(
                input,
                SaveMode.INSERT_ONLY
            )
        }catch (e: SaveException.NotUnique){
            throw BusinessException("该用户已存在")
        }
        
    }

    fun findUserByEmailOrNumber(id: String): User {
        try {

            val user = sqlClient
                .createQuery(User::class) {
                    where(or(
                        table.email eq id,
                        table.userNumber eq id
                    ))
                    select(table)
                }
                .fetchOne()
            return user
        }catch (e: EmptyResultException){
            throw BusinessException("该用户不存在")
        }
    }

    fun updatePassword(input: ResetPasswordInput){
        try {
            sqlClient.update(
                input
            )
        }catch (e: SaveException.NotUnique){
            throw BusinessException("该用户已存在")
        }

    }



}