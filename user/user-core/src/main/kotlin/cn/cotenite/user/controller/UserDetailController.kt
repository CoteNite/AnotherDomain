package cn.cotenite.user.controller

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import cn.cotenite.user.command.UserDetailCommand
import cn.cotenite.user.dto.UserDetailCreateDTO
import cn.cotenite.user.model.domain.dto.UserDetailUpdateInput
import cn.cotenite.user.model.domain.dto.UserDetailView
import cn.cotenite.user.query.UserDetailQuery
import cn.cotenite.utils.UserIdContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/28 01:30
 */
@Validated
@RestController
@RequestMapping("/userDetail")
class UserDetailController(
    private val userDetailQuery: UserDetailQuery,
    private val userDetailCommand: UserDetailCommand
){

    @GetMapping("/getAll")
    fun getAll(): UserDetailView {
        return userDetailQuery.getUserDetailAll()
    }

    @PostMapping("/updateUser")
    fun updateUserDetail(input: UserDetailUpdateInput){
        userDetailCommand.updateUserDetail(input)
    }



}