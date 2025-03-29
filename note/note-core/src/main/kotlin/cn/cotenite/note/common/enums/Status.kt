package cn.cotenite.note.common.enums

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import org.babyfish.jimmer.sql.EnumType

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 02:43
 */
@EnumType(EnumType.Strategy.ORDINAL)
enum class Status(
    val code:Int
){
    //审核
    REVIEW(0),
    //展示
    SHOW(1),
    //下架
    SHUT_DOWN(2);

    companion object{

        fun getStatusByCode(code:Int):Status{
            return when(code){
                0 -> REVIEW
                1 -> SHOW
                2 -> SHUT_DOWN
                else -> throw BusinessException(Errors.PARAM_VALIDATION_ERROR)
            }
        }
    }


}