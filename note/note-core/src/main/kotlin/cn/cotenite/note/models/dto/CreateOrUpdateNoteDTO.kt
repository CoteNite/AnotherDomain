package cn.cotenite.note.models.dto

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import cn.cotenite.note.common.enums.Type
import cn.cotenite.note.common.enums.Type.TEXT
import cn.cotenite.note.common.enums.Type.VIDEO

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 06:05
 */
data class CreateOrUpdateNoteDTO(

    val top: Int,

    val type: Int,

    val visible: Int,

    val title: String,

    val content:String?,

    val imgUris:String?,

    val videoUri:String?
){
    fun checkParam(){
        val type = Type.getTypeByCode(this.type)

        when (type) {

            TEXT -> {
                if ((imgUris!=null||content!=null)&&videoUri==null){
                    throw BusinessException(Errors.PARAM_VALIDATION_ERROR)
                }
            }

            VIDEO -> {
                if (imgUris!=null||videoUri==null){
                    throw BusinessException(Errors.PARAM_VALIDATION_ERROR)
                }
            }

        }

    }

}