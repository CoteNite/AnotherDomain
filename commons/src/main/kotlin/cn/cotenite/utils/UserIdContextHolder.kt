package cn.cotenite.utils

import cn.cotenite.filter.LoginUserContextHolder

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/27 07:52
 */
object UserIdContextHolder {

    fun getId():Long{
        return LoginUserContextHolder.getUserId()
    }
}