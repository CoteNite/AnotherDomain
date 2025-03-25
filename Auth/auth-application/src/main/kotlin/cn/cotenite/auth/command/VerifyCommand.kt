package cn.cotenite.auth.command

import cn.cotenite.auth.commons.utils.RedisKeyCreator
import cn.cotenite.auth.repo.VerifyRepository
import cn.cotenite.auth.model.dto.req.CaptchaReqDTO
import cn.hutool.captcha.CaptchaUtil
import org.springframework.stereotype.Service

/**
 * @Author  RichardYoung
 * @Description  
 * @Date  2025/3/16 06:15
*/
interface VerifyCommand {

    fun handleLoginCode(): CaptchaReqDTO

    fun handleRegisterCode(email:String,code:String)

    fun handleCheck(verifyKey:String,code:String)

    fun checkExist(key: String)

    fun handleRestPasswordCode(email:String,code:String)
}

@Service
class VerifyCommandImpl(
    private val verifyRepository: VerifyRepository
): VerifyCommand {


    override fun handleLoginCode(): CaptchaReqDTO {
        val captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4)
        val imageBase64 = captcha.imageBase64
        val code = captcha.code
        val key = verifyRepository.saveLoginKey(code)
        return CaptchaReqDTO(key,imageBase64)
    }

    override fun handleRegisterCode(email:String,code:String){
        val newKey= RedisKeyCreator.registerCodeKey(email)
        verifyRepository.saveRegisterKey(newKey,code)
    }

    override fun handleCheck(verifyKey:String,code:String) {
        val verifyAgg = verifyRepository.getVerifyAgg(verifyKey)
        verifyAgg.verify(code)
    }

    override fun checkExist(key: String) {
        if (verifyRepository.checkExist(key)) {
            throw Exception("验证码已经发送，有效期三分钟，请检查您的邮箱")
        }
    }

    override fun handleRestPasswordCode(email: String,code:String) {
        val newKey= RedisKeyCreator.resetPasswordCodeKey(email)
        verifyRepository.saveRegisterKey(newKey,code)
    }

}