package cn.cotenite.note.common.enums

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import org.babyfish.jimmer.sql.EnumType

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 04:10
 */
@EnumType(EnumType.Strategy.ORDINAL)
enum class Top(
    val code:Int
){

    UN_TOP(0),

    TOP(1);


    companion object{
        fun getTopByCode(code:Int):Top{
            return when(code){
                0 -> UN_TOP
                1 -> TOP
                else -> throw BusinessException(Errors.PARAM_VALIDATION_ERROR)
            }
        }
    }



}