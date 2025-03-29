package cn.cotenite.auth.model.domain

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException


/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/16 03:26
 */
class Verify(
    private val verifyKey:String,
    private val verifyCode:String,
){
    fun verify(code:String){
        if (code != verifyCode) {
            throw BusinessException(Errors.PARAM_VALIDATION_ERROR)
        }
    }

}