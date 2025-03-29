package cn.cotenite.user.command

import cn.cotenite.user.dto.UserDetailCreateDTO
import cn.cotenite.user.model.domain.dto.UserDetailSaveInput
import cn.cotenite.user.query.UserDetailDubboCommand
import cn.cotenite.user.repository.UserDetailRepository
import io.seata.spring.annotation.GlobalTransactional
import org.apache.dubbo.config.annotation.DubboService
import org.springframework.web.bind.annotation.CrossOrigin

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/25 16:01
 */
@CrossOrigin("*")
@DubboService(version = "1.0")
class UserDetailDubboCommandImpl(
    private val userDetailRepository: UserDetailRepository
): UserDetailDubboCommand {

    override fun createUserDetail(userDetailCreateDTO: UserDetailCreateDTO) {
        val userDetailSaveInput = userDetailCreateDTO.run{ UserDetailSaveInput(id, userNum, email) }
        userDetailRepository.saveUserDetail4Create(userDetailSaveInput)
    }

    override fun deleteUserById(id: Long) {
        userDetailRepository.deleteUserDetailById(id)
    }

}