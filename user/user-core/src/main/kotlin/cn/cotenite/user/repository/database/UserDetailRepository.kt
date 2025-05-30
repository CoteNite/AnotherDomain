package cn.cotenite.user.repository.database

import cn.cotenite.user.model.domain.UserDetail
import cn.cotenite.user.model.domain.dto.UserDetailSaveInput
import cn.cotenite.user.model.domain.dto.UserDetailSimpleView
import cn.cotenite.user.model.domain.dto.UserDetailUpdateInput
import cn.cotenite.user.model.domain.dto.UserDetailView
import cn.cotenite.user.model.domain.id
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.exists
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

    fun getUserDetailAll(userId: Long): UserDetailView {
        return sqlClient.createQuery(UserDetail::class){
            table.id eq userId
            select(
                table.fetch(UserDetailView::class)
            )
        }.fetchOne()

    }

    fun updateUserDetail(input: UserDetailUpdateInput) {
        sqlClient.update(input)
    }

    fun deleteUserDetailById(id: Long) {
        sqlClient.createDelete(UserDetail::class){
            where(table.id eq id)
        }
    }

    fun getUserDetailSimpleView(userId: Long): UserDetailSimpleView {
        return sqlClient.createQuery(UserDetail::class) {
            where(table.id eq userId)
            select(
                table.fetch(UserDetailSimpleView::class)
            )
        }.fetchOne()
    }

    fun checkUserDetailExist(userId: Long): Boolean {
        return sqlClient.createQuery(UserDetail::class) {
            select(
                exists(subQuery(UserDetail::class) {
                    where(table.id eq userId)
                    select(table)
                })
            )
        }.fetchOne()
    }


}