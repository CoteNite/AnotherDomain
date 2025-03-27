package cn.cotenite.user.query

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import cn.cotenite.user.model.domain.dto.UserDetailUpdateInput
import cn.cotenite.user.model.domain.dto.UserDetailView
import cn.cotenite.user.repository.UserDetailRepository
import cn.cotenite.utils.UserIdContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/27 07:49
 */
@RestController
@RequestMapping("user")
class UserDetailQuery(
    private val userDetailRepository: UserDetailRepository
){

    @GetMapping("/getAll")
    fun getUserDetailAll(): UserDetailView {
        val id = UserIdContextHolder.getId()
        return userDetailRepository.getUserDetailAll(id)
    }

    @PostMapping("/updateUser")
    fun updateUserDetail(input: UserDetailUpdateInput){
        val id = UserIdContextHolder.getId()
        if (input.id != id){
            throw BusinessException(Errors.UNAUTHORIZED)
        }
        userDetailRepository.updateUserDetail(input)
    }

}