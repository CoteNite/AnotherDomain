package cn.cotenite.note.common.enums

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 02:45
 */

enum class Type(
    val code:Int
){

    TEXT(1),

    VIDEO(2);

    companion object{
        fun getTypeByCode(code:Int):Type{
            return when(code){
                1 -> TEXT
                2 -> VIDEO
                else -> throw BusinessException(Errors.PARAM_VALIDATION_ERROR)
            }
        }
    }




}