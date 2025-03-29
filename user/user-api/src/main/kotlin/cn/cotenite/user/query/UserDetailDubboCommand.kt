package cn.cotenite.user.query

import cn.cotenite.user.dto.UserDetailCreateDTO

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/25 15:58
 */
interface UserDetailDubboCommand{

    fun createUserDetail(userDetailCreateDTO: UserDetailCreateDTO)
    fun deleteUserById(id: Long)

}