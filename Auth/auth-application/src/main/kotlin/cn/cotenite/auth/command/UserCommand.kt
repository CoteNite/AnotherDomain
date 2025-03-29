package cn.cotenite.auth.command

import cn.cotenite.auth.commons.enums.RoleCommons
import cn.cotenite.auth.model.domain.User
import cn.cotenite.auth.model.domain.dto.dto.ResetPasswordInput
import cn.cotenite.auth.model.domain.dto.dto.UserInput
import cn.cotenite.auth.model.po.dto.UserRoleSaveInput
import cn.cotenite.auth.repo.UserRepository
import cn.cotenite.auth.repo.UserRoleRepository
import cn.cotenite.user.dto.UserDetailCreateDTO
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

    fun handleRegister(email:String,password:String):UserDetailCreateDTO

    fun handleLogin(id:String,password:String)

    fun handleRestPassword(restPasswordInput: ResetPasswordInput)

    fun cancelUser(userId: Long): User

}

@Service
class UserCommandImpl(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
    private val passWordEncoder: PasswordEncoder
): UserCommand {

    @Transactional(rollbackFor = [Exception::class])
    override fun handleRegister(email: String, password: String):UserDetailCreateDTO{
        val initUserNum = RandomUtil.randomString(9)
        val encode = passWordEncoder.encode(password)
        val input= UserInput(email,encode,initUserNum)
        val userId = userRepository.saveUser(input)
        val userRoleInput=UserRoleSaveInput(UserRoleSaveInput.TargetOf_user(userId), UserRoleSaveInput.TargetOf_role(RoleCommons.USER.roleId))
        userRoleRepository.saveUserRole4Register(userRoleInput)
        return UserDetailCreateDTO(userId,initUserNum,email)
    }

    override fun handleLogin(id: String, password: String){
        val user=userRepository.login(id)
        user.login(password)
    }

    override fun handleRestPassword(restPasswordInput: ResetPasswordInput) {
       val input=ResetPasswordInput(
           restPasswordInput.email,
           passWordEncoder.encode(restPasswordInput.password),
           restPasswordInput.verifyCode
       )
        userRepository.updatePassword(input)
    }

    override fun cancelUser(userId: Long): User {
        val user = userRepository.findOneUserById(userId)
        userRepository.deleteUserById(user)
        userRoleRepository.deleteRelationByUserId(userId)
        return user
    }

}