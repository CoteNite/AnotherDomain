package cn.cotenite.auth.command

import cn.cotenite.auth.model.domain.dto.dto.ResetPasswordInput
import cn.cotenite.auth.model.domain.dto.dto.UserInput
import cn.cotenite.auth.repo.UserRepository
import cn.cotenite.utils.SnowFlakeGenerator
import cn.hutool.core.util.RandomUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/**
 * @Author  RichardYoung
 * @Description  
 * @Date  2025/3/16 06:26
*/
interface UserCommand{

    fun handleRegister(email:String,password:String)

    fun handleLogin(id:String,password:String):Long

    fun handleRestPassword(restPasswordInput: ResetPasswordInput)

}

@Service
class UserCommandImpl(
    private val userRepository: UserRepository,
    private val passWordEncoder: PasswordEncoder,
): UserCommand {

    override fun handleRegister(email: String, password: String) {
        val initUserNum = RandomUtil.randomString(9)
        val encode = passWordEncoder.encode(password)
        val snowflake=SnowFlakeGenerator.generate();
        val input= UserInput(email,snowflake,encode,initUserNum)
        userRepository.saveUser(input)

    }

    override fun handleLogin(id: String, password: String):Long{
        val user=userRepository.findUserByEmailOrNumber(id)
        return user.verify(password)

    }

    override fun handleRestPassword(restPasswordInput: ResetPasswordInput) {
       val input=ResetPasswordInput(
           restPasswordInput.email,
           passWordEncoder.encode(restPasswordInput.password),
           restPasswordInput.verifyCode
       )
        userRepository.updatePassword(input)
    }

}