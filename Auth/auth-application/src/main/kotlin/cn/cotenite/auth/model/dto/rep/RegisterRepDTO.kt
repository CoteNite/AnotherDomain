package cn.cotenite.auth.model.dto.rep

import cn.cotenite.validator.Email
import jakarta.validation.constraints.Pattern

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/16 06:22
 */
data class RegisterRepDTO(

    @field:Pattern(regexp = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}", message = "邮箱格式不正确")
    val email:String,
    val password:String,
    val verifyCode:String
)
