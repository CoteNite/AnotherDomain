package cn.cotenite.auth.query

import cn.cotenite.auth.repo.UserRepository
import org.springframework.stereotype.Service

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 00:55
 */
interface UserQuery{

    fun getEmailByUserId(userId:Long): String

}

@Service
class UserQueryImpl(
    private val userRepository: UserRepository
):UserQuery {
    override fun getEmailByUserId(userId: Long):String{
        return userRepository.getEmailByUserId(userId)
    }

}