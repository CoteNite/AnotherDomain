package cn.cotenite.auth.query

import cn.cotenite.auth.model.domain.dto.dto.UserDetailSaveInput
import cn.cotenite.auth.repo.UserDetailRepository
import org.springframework.stereotype.Service

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/19 07:28
 */
@Service
class UserDetailQuery(
    private val userDetailRepository: UserDetailRepository
){
    fun initUserDetail(input: UserDetailSaveInput){
        return userDetailRepository.insertUserDetail(input)
    }
}