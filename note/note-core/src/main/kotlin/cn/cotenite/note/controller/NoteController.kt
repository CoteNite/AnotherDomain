package cn.cotenite.note.controller

import cn.cotenite.filter.LoginUserContextHolder
import cn.cotenite.note.command.NoteCommand
import cn.cotenite.note.common.enums.Status
import cn.cotenite.note.common.enums.Top
import cn.cotenite.note.common.enums.Type
import cn.cotenite.note.common.enums.Visible
import cn.cotenite.note.models.dto.CreateNoteDTO
import cn.cotenite.note.models.dto.NoteCreateInput
import cn.cotenite.note.models.dto.NoteDetailCreateInput
import cn.cotenite.note.models.read.NoteDetail
import cn.cotenite.note.service.NoteService
import org.springframework.transaction.annotation.Transactional
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
    private val noteService: NoteService
){

    @PostMapping("/create")
    fun createNote(@RequestBody createNoteDTO: CreateNoteDTO) {
        noteService.createNote(createNoteDTO)
    }

}