package cn.cotenite.auth.model.dto.req

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/16 06:02
 */
data class CaptchaReqDTO(
    val key:String,
    val imageBase64:String
)
