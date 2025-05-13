package cn.cotenite.note.model.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/14 01:45
 */
data class VideoNoteUpdateRequest(
    val id:Long,
    @Max(1)
    @Min(0)
    val top:Int,
    @Max(2)
    @Min(0)
    val visible:Int,
    @Size(min = 1, max = 64)
    val title:String,
    val tag:String?,
    val contentUuid:String?,
    val content:String?,
    val videoUri:String
)