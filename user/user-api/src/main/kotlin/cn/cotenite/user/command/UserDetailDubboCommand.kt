package cn.cotenite.user.command

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