package cn.cotenite.repo.database

import cn.cotenite.model.domain.Follow
import cn.cotenite.model.domain.dto.FollowView
import cn.cotenite.model.domain.followingUserId
import cn.cotenite.model.domain.userId
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.stereotype.Repository

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/17 23:21
 */
@Repository
class FollowRepository(
    private val sqlClient: KSqlClient
){
    fun addFollow(userId: Long, followUserId: Long) {
        sqlClient.createUpdate(Follow::class) {
            set(table.userId, userId)
            set(table.followingUserId, followUserId)
            SaveMode.INSERT_IF_ABSENT
        }.execute()
    }

    fun getFollowingList(userId: Long): List<FollowView> {
        return sqlClient.createQuery(Follow::class) {
            where(table.userId eq userId)
            select(table.fetch(FollowView::class))
        }.execute()
    }
}