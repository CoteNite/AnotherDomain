package cn.cotenite.repo.database

import org.babyfish.jimmer.sql.kt.KSqlClient
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
}