package cn.cotenite.user.command

import cn.cotenite.user.model.domain.dto.UserDetailUpdateInput
import cn.cotenite.user.repository.database.UserDetailRepository
import cn.cotenite.utils.UserIdContextHolder
import org.springframework.stereotype.Service

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/28 01:47
 */
interface UserDetailCommand {
    fun updateUserDetail(input: UserDetailUpdateInput)
}

@Service
class UserDetailCommandImpl(
    private val userDetailRepository: UserDetailRepository
): UserDetailCommand {
    override fun updateUserDetail( input: UserDetailUpdateInput){
        UserIdContextHolder.verifyUser(input.id)
        userDetailRepository.updateUserDetail(input)
    }
}