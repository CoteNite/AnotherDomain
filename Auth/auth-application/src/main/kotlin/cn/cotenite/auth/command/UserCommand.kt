package cn.cotenite.auth.command

import cn.cotenite.auth.commons.enums.RoleCommons
import cn.cotenite.auth.commons.utils.SnowflakeIdGenerator
import cn.cotenite.auth.model.domain.dto.dto.ResetPasswordInput
import cn.cotenite.auth.model.domain.dto.dto.UserDetailSaveInput
import cn.cotenite.auth.model.domain.dto.dto.UserInput
import cn.cotenite.auth.model.po.dto.UserRoleSaveInput
import cn.cotenite.auth.query.UserDetailQuery
import cn.cotenite.auth.repo.UserRepository
import cn.cotenite.auth.repo.UserRoleRepository
import cn.hutool.core.util.RandomUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
    private val userDetailQuery: UserDetailQuery,
    private val userRoleRepository: UserRoleRepository,
    private val passWordEncoder: PasswordEncoder,
    private val snowflakeIdGenerator: SnowflakeIdGenerator
): UserCommand {

    @Transactional(rollbackFor = [Exception::class])
    override fun handleRegister(email: String, password: String) {
        val initUserNum = RandomUtil.randomString(9)
        val encode = passWordEncoder.encode(password)
        val snowflake=snowflakeIdGenerator.getId()
        val input= UserInput(email,snowflake,encode,initUserNum)
        val userId = userRepository.saveUser(input)
        val userRoleInput=UserRoleSaveInput(UserRoleSaveInput.TargetOf_user(userId), UserRoleSaveInput.TargetOf_role(RoleCommons.USER.roleId))
        userRoleRepository.saveUserRole4Register(userRoleInput)
        val userDetailInput=UserDetailSaveInput(snowflake,"用户${initUserNum}")
        userDetailQuery.initUserDetail(userDetailInput)
    }

    override fun handleLogin(id: String, password: String):Long{
        val user=userRepository.findUser4Register(id)
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