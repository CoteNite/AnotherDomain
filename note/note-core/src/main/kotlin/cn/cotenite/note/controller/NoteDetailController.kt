package cn.cotenite.note.controller

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import cn.cotenite.note.command.NoteDetailCommand
import cn.cotenite.note.models.dto.TextNoteDetailUpdateInput
import cn.cotenite.note.models.dto.VideoNoteDetailUpdateInput
import cn.cotenite.note.query.NoteDetailQuery
import cn.cotenite.note.service.NoteService
import cn.cotenite.page.BasePage
import cn.cotenite.response.Response
import cn.hutool.core.util.StrUtil
import org.springframework.web.bind.annotation.*

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 10:44
 */
@RestController
@RequestMapping("/noteDetail")
class NoteDetailController(
    private val noteService: NoteService,
    private val noteDetailQuery: NoteDetailQuery
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

    @PostMapping("/findNoteDetailViewList")
    fun findNoteDetailViewList(@RequestBody page: BasePage): Response {
        val noteDetailViewList = noteDetailQuery.findNoteDetailViewList(page)
        return Response.success(noteDetailViewList)
    }


    @GetMapping("/textNoteDetailInfo/{id}")
    fun getTextNoteDetailInfo(@PathVariable id: Long): Response {
        val textNoteDetailInfo = noteDetailQuery.findTextNoteDetailInfo(id)
        return Response.success(textNoteDetailInfo)
    }

    @GetMapping("/videoNoteDetailInfo/{id}")
    fun getVideoNoteDetailInfo(@PathVariable id: Long): Response {
        val videoNoteDetailInfo = noteDetailQuery.findVideoNoteDetailInfo(id)
        return Response.success(videoNoteDetailInfo)
    }
}