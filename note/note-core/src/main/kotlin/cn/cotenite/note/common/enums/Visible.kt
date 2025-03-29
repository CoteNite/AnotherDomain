package cn.cotenite.note.common.enums

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 04:02
 */
enum class Visible(
    val code:Int
){

    //对所有人展示
    SHOW(1),

    //仅对自己展示
    SELF(2);


    companion object{
        fun getVisibleByCode(code:Int):Visible{
            return when(code){
                1 -> SHOW
                2 -> SELF
                else -> throw BusinessException(Errors.PARAM_VALIDATION_ERROR)
            }
        }
    }


}