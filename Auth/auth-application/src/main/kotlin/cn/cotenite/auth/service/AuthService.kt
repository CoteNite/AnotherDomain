package cn.cotenite.auth.service

import cn.cotenite.auth.command.UserCommand
import cn.cotenite.auth.command.VerifyCommand
import cn.cotenite.auth.policy.EmailPolicy
import cn.cotenite.auth.commons.utils.RedisKeyCreator
import cn.cotenite.auth.model.domain.dto.dto.ResetPasswordInput
import cn.dev33.satoken.stp.StpUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/17 03:57
 */
interface AuthService {
    fun sendEmail4Register(email:String)

    fun doRegister(email:String, password:String, verifyCode:String)

    fun doLogin(number:String, password:String, verifyKey:String, verifyCode:String):Long

    fun resetPassword(restPasswordInput: ResetPasswordInput)

    fun sendEmail4RestPassword(email: String)

}

@Service
class AuthServiceImpl(
    private val verifyCommand: VerifyCommand,
    private val userCommand: UserCommand,
    private val sendUtils: EmailPolicy
):AuthService{

    @Transactional(rollbackFor = [Exception::class])
    override fun sendEmail4Register(email: String) {
        verifyCommand.checkExist(RedisKeyCreator.registerCodeKey(email))
        sendUtils.sendMail4Register(email)
    }

    override fun doRegister(email: String, password: String, verifyCode: String) {
        val key = RedisKeyCreator.registerCodeKey(email)
        verifyCommand.handleCheck(key, verifyCode)
        userCommand.handleRegister(email,password)
    }


    override fun doLogin(number:String, password:String, verifyKey:String,verifyCode:String):Long {
        val key = RedisKeyCreator.loginCodeKey(verifyKey)
        verifyCommand.handleCheck(key,verifyCode)
        val loginId = userCommand.handleLogin(number, password)
        StpUtil.login(loginId)
        return loginId

    }

    override fun resetPassword(restPasswordInput: ResetPasswordInput) {
        verifyCommand.handleCheck(RedisKeyCreator.resetPasswordCodeKey(restPasswordInput.email),restPasswordInput.verifyCode)
        userCommand.handleRestPassword(restPasswordInput)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun sendEmail4RestPassword(email: String) {
        verifyCommand.checkExist(RedisKeyCreator.registerCodeKey(email))
        sendUtils.sendMail4ResetPassword(email)
    }


}
