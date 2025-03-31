package cn.cotenite.note.controller

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import cn.cotenite.note.command.NoteDetailCommand
import cn.cotenite.note.models.dto.TextNoteDetailUpdateInput
import cn.cotenite.note.models.dto.VideoNoteDetailUpdateInput
import cn.cotenite.note.service.NoteService
import cn.cotenite.response.Response
import cn.hutool.core.util.StrUtil
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 10:44
 */
@RestController
@RequestMapping("/noteDetail")
class NoteDetailController(
    private val noteService: NoteService
){

    @PostMapping("/updateVideoNote")
    fun createNoteDetail(@RequestBody videoNoteDetailUpdateInput: VideoNoteDetailUpdateInput): Response {
        if (videoNoteDetailUpdateInput.videoUri == null&&(StrUtil.isEmpty(videoNoteDetailUpdateInput.title)||StrUtil.isEmpty(videoNoteDetailUpdateInput.contentUUID))){
            throw BusinessException(Errors.PARAM_VALIDATION_ERROR)
        }
        noteService.updateVideoNoteDetail(videoNoteDetailUpdateInput)
        return Response.success()
    }


    @PostMapping("/updateTextNote")
    fun createTextDetail(@RequestBody textNoteDetailUpdateInput: TextNoteDetailUpdateInput): Response {
        if (textNoteDetailUpdateInput.contentUUID == null&&textNoteDetailUpdateInput.imgUris==null){
            throw BusinessException(Errors.PARAM_VALIDATION_ERROR)
        }
        noteService.updateTextNoteDetail(textNoteDetailUpdateInput)
        return Response.success()
    }
}