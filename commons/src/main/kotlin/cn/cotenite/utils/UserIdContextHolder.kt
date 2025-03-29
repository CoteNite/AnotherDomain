package cn.cotenite.utils

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
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

    fun verifyUser(value:Long){
        if (LoginUserContextHolder.getUserId() != value){
            throw BusinessException(Errors.UNAUTHORIZED)
        }
    }
}