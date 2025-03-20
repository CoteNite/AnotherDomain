package cn.cotenite.auth.model.domain


/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/16 03:26
 */
class Verify(
    private val verifyKey:String,
    private val verifyCode:String,
){
    fun verify(code:String){
        if (code != verifyCode) {
            throw Exception("验证码错误")
        }
    }

}