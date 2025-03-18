package cn.cotenite.auth.model.dto.req

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/17 02:51
 */
data class LoginReqDTO(
    val number:String,
    val password:String,
    val verifyKey:String,
    val verifyCode:String
)
