package cn.cotenite.controller

import cn.cotenite.command.FollowCommand
import cn.cotenite.model.request.FollowRequest
import cn.cotenite.response.Response
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/17 23:18
 */
@RestController
@RequestMapping("/follow")
class FollowController(
    private val followCommand: FollowCommand
) {

    @PostMapping("/follow")
    fun follow(@RequestBody @Validated followRequest: FollowRequest):Response{
        followCommand.follow(followRequest.followUserId)
        return Response.success()
    }
}