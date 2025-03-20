package cn.cotenite.auth.repo


import cn.cotenite.asp.Slf4j
import cn.cotenite.auth.commons.utils.RedisKeyCreator
import cn.cotenite.auth.model.domain.agg.*

import cn.cotenite.auth.model.domain.dto.dto.ResetPasswordInput
import cn.cotenite.auth.model.domain.dto.dto.UserInput
import cn.cotenite.commons.YesOrNo

import cn.cotenite.expection.BusinessException
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.exception.EmptyResultException
import org.babyfish.jimmer.sql.exception.SaveException
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.or
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Repository
import java.util.HashMap

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/16 06:27
 */
@Slf4j
@Repository
class UserRepository(
    private val sqlClient: KSqlClient,
    private val redisClient: RedissonClient
) {
    fun saveUser(input: UserInput):Long{
        try {
            val id = sqlClient.save(
                input,
                SaveMode.INSERT_ONLY
            )
                .modifiedEntity
                .id
            return id
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
                    select(table.fetchBy {
                        allScalarFields()
                        roles {
                            permissions {
                                permissionName()
                            }
                        }
                    })
                }
                .fetchOne()
            val permissionHash = HashMap<String,Int>()
            for (role in user.roles){
                for (permission in role.permissions){
                    permissionHash[permission.permissionName] = YesOrNo.YES.id
                }
            }
            redisClient.getBucket<HashMap<String,Int>>(RedisKeyCreator.userPermissionHashKey(user.id)).set(permissionHash)
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