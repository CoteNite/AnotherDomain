package cn.cotenite.auth.service

import cn.cotenite.asp.Slf4j
import cn.cotenite.auth.command.UserCommand
import cn.cotenite.auth.command.VerifyCommand
import cn.cotenite.auth.policy.EmailPolicy
import cn.cotenite.auth.commons.utils.RedisKeyCreator
import cn.cotenite.auth.model.domain.dto.dto.ResetPasswordInput
import cn.cotenite.auth.query.UserQuery
import cn.cotenite.user.command.UserDetailDubboCommand
import org.apache.dubbo.config.annotation.DubboReference
import org.apache.seata.spring.annotation.GlobalTransactional
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

    fun doLogin(number:String, password:String, verifyKey:String, verifyCode:String)

    fun resetPassword(restPasswordInput: ResetPasswordInput)

    fun sendEmail4RestPassword(email: String)

    fun doCancel(userId: Long, verifyCode: String)

    fun doGetCancelCode(userId: Long)
}

@Slf4j
@Service
class AuthServiceImpl(
    private val verifyCommand: VerifyCommand,
    private val userQuery: UserQuery,
    private val userCommand: UserCommand,
    private val emailPolicy: EmailPolicy,
    @DubboReference(interfaceClass = UserDetailDubboCommand::class, version = "1.0")
    private val userDetailDubboCommand: UserDetailDubboCommand
):AuthService{



    @Transactional(rollbackFor = [Exception::class])
    override fun sendEmail4Register(email: String) {
        verifyCommand.checkExist(RedisKeyCreator.registerCodeKey(email))
        emailPolicy.sendMail4Register(email)
    }

    @GlobalTransactional(rollbackFor = [Exception::class])
    override fun doRegister(email: String, password: String, verifyCode: String) {
        val key = RedisKeyCreator.registerCodeKey(email)
        verifyCommand.handleCheck(key, verifyCode)
        val userDetailCreateDTO = userCommand.handleRegister(email, password)
        userDetailDubboCommand.createUserDetail(userDetailCreateDTO)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun doLogin(number:String, password:String, verifyKey:String,verifyCode:String) {
        val key = RedisKeyCreator.loginCodeKey(verifyKey)
        verifyCommand.handleCheck(key,verifyCode)
        userCommand.handleLogin(number, password)
    }

    override fun resetPassword(restPasswordInput: ResetPasswordInput) {
        verifyCommand.handleCheck(RedisKeyCreator.resetPasswordCodeKey(restPasswordInput.email),restPasswordInput.verifyCode)
        userCommand.handleRestPassword(restPasswordInput)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun sendEmail4RestPassword(email: String) {
        verifyCommand.checkExist(RedisKeyCreator.registerCodeKey(email))
        emailPolicy.sendMail4ResetPassword(email)
    }

    @Transactional(rollbackFor = [Exception::class])
    @GlobalTransactional(rollbackFor = [Exception::class])
    override fun doCancel(userId: Long, verifyCode: String) {
        val user = userCommand.cancelUser(userId)
        verifyCommand.handleCheck(RedisKeyCreator.cancelKey(user.email), verifyCode)
        userDetailDubboCommand.deleteUserById(user.id)
    }

    override fun doGetCancelCode(userId: Long) {
        val email = userQuery.getEmailByUserId(userId)
        emailPolicy.sendMail4Cancel(email)
    }


}
