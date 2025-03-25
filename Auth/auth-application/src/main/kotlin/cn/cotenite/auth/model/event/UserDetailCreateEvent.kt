package cn.cotenite.auth.model.event

import cn.cotenite.auth.model.read.UserDetail

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/25 14:33
 */
data class UserDetailCreateEvent(
    val id:Long,
    val userNum:String,
    val email:String
)
