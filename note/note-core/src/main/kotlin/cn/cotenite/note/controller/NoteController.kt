package cn.cotenite.note.controller

import cn.cotenite.note.command.NoteCommand
import cn.cotenite.note.model.request.NoteAddRequest
import cn.cotenite.response.Response
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
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
    fun addNote(@Validated request: NoteAddRequest):Response{
        request.check()
        noteCommand.addNote(request)
        return Response.success()
    }

}