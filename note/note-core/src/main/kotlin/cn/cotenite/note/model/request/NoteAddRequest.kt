package cn.cotenite.note.model.request

import cn.cotenite.expection.BusinessException
import cn.cotenite.note.common.enums.Type
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import org.babyfish.jimmer.sql.kt.ast.expression.case

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/13 07:04
 */
data class NoteAddRequest(
    val creatorId:Long,
    @Max(1)
    @Min(0)
    val top:Int,
    @Max(1)
    @Min(0)
    val type:Int,
    @Max(2)
    @Min(0)
    val visible:Int,
    @Size(min = 1, max = 64)
    val title:String,
    val tag:String?,
    val content:String?,
    val imgUris:String?,
    val videoUri:String?
){

    fun check(){
        when(type){
            Type.IMAGE_CONTENT.code -> {
                if (imgUris == null&&content == null){
                    throw BusinessException("图片帖子必须上传图片")
                }
            }

            Type.VIDEO_CONTENT.code->{
                if (videoUri == null){
                    throw BusinessException("视频帖子必须上传视频")
                }
            }
        }
    }

}