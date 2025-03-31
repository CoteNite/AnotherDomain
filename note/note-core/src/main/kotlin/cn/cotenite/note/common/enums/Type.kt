package cn.cotenite.note.common.enums

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import org.babyfish.jimmer.sql.EnumType

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 02:45
 */
@EnumType(EnumType.Strategy.ORDINAL)
enum class Type(
    val code:Int
){
    //图文or纯文
    TEXT(0),

    VIDEO(1);

    companion object{
        fun getTypeByCode(code:Int):Type{
            return when(code){
                0 -> TEXT
                1 -> VIDEO
                else -> throw BusinessException(Errors.PARAM_VALIDATION_ERROR)
            }
        }
    }




}