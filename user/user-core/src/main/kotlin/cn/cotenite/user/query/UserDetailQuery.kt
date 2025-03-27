package cn.cotenite.user.query

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import cn.cotenite.user.model.domain.dto.UserDetailUpdateInput
import cn.cotenite.user.model.domain.dto.UserDetailView
import cn.cotenite.user.repository.UserDetailRepository
import cn.cotenite.utils.UserIdContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/27 07:49
 */
interface UserDetailQuery {
    fun getUserDetailAll(): UserDetailView
}

@Service
class UserDetailQueryImpl(
    private val userDetailRepository: UserDetailRepository
):UserDetailQuery{

    override fun getUserDetailAll(): UserDetailView {
        val id = UserIdContextHolder.getId()
        return userDetailRepository.getUserDetailAll(id)
    }



}