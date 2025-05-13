package cn.cotenite.note.controller

import cn.cotenite.note.command.NoteCommand
import cn.cotenite.note.model.request.ImageNoteUpdateRequest
import cn.cotenite.note.model.request.NoteAddRequest
import cn.cotenite.note.model.request.VideoNoteUpdateRequest
import cn.cotenite.response.Response
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/13 05:57
 */
@RestController
@RequestMapping("/note")
class NoteController(
    private val noteCommand: NoteCommand
){

    @PostMapping("/addNote")
    fun addNote(@RequestBody @Validated request: NoteAddRequest):Response{
        request.check()
        noteCommand.addNote(request)
        return Response.success()
    }

    @PostMapping("/updateImageNote")
    fun updateImageNote(@RequestBody @Validated request: ImageNoteUpdateRequest):Response{
        noteCommand.updateImageNote(request)
        return Response.success()
    }

    @PostMapping("/updateVideoNote")
    fun updateVideoNote(@RequestBody @Validated request: VideoNoteUpdateRequest):Response{
        noteCommand.updateVideoNote(request)
        return Response.success()
    }

    @PostMapping("/deleteNote")
    fun deleteNote(@RequestBody id:Long):Response{
        noteCommand.deleteNote(id)
        return Response.success()
    }

}