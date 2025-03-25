package cn.cotenite.user.dto

import java.io.Serializable

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/25 15:59
 */
data class UserDetailCreateDTO(
    val id:Long,
    val userNum:String,
    val email:String
): Serializable
