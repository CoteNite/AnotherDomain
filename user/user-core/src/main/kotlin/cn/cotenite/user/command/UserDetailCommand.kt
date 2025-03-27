package cn.cotenite.user.command

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import cn.cotenite.user.model.domain.dto.UserDetailUpdateInput
import cn.cotenite.user.repository.UserDetailRepository
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
        val id = UserIdContextHolder.getId()
        if (input.id != id){
            throw BusinessException(Errors.UNAUTHORIZED)
        }
        userDetailRepository.updateUserDetail(input)
    }
}