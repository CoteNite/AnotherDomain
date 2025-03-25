package cn.cotenite.user.query

import cn.cotenite.user.dto.UserDetailCreateDTO
import cn.cotenite.user.model.domain.dto.UserDetailSaveInput
import cn.cotenite.user.repository.UserDetailRepository
import org.apache.dubbo.config.annotation.DubboService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/25 16:01
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/user")
@DubboService(version = "1.0")
class UserDetailCreateQueryImpl(
    val userDetailRepository: UserDetailRepository
):UserDetailCreateQuery {

    override fun createUserDetail(userDetailCreateDTO: UserDetailCreateDTO) {
        val userDetailSaveInput = userDetailCreateDTO.run{ UserDetailSaveInput(id, userNum, email) }
        userDetailRepository.saveUserDetail4Create(userDetailSaveInput)
    }

}