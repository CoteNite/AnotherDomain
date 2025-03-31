package cn.cotenite.note.controller

import cn.cotenite.note.command.NoteCommand
import cn.cotenite.note.models.dto.CreateOrUpdateNoteDTO
import cn.cotenite.note.models.dto.NoteUserUpdateInput
import cn.cotenite.note.service.NoteService
import cn.cotenite.response.Response
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 02:36
 */
@RestController
@RequestMapping("/note")
class NoteController(
    private val noteService: NoteService,
    private val noteCommand: NoteCommand
){

    @PostMapping("/create")
    fun createNote(@RequestBody createOrUpdateNoteDTO: CreateOrUpdateNoteDTO):Response{
        createOrUpdateNoteDTO.checkParam()
        noteService.createNote(createOrUpdateNoteDTO)
        return Response.success()
    }

    @PostMapping("/update")
    fun updateNote(@RequestBody noteUserUpdateInput: NoteUserUpdateInput):Response{
        noteCommand.updateNote(noteUserUpdateInput)
        return Response.success()
    }
}