package cn.cotenite.note.command

import cn.cotenite.note.models.dto.NoteCreateInput
import cn.cotenite.note.models.dto.NoteUserUpdateInput
//import cn.cotenite.note.models.dto.NoteUserUpdateInput
import cn.cotenite.note.repo.NoteRepository
import org.springframework.stereotype.Service

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 05:47
 */
interface NoteCommand {
    fun createNote(noteCreateInput: NoteCreateInput):Long
    fun updateNote(noteUserUpdateInput: NoteUserUpdateInput)
}

@Service
class NoteCommandImpl(
    private val noteRepository: NoteRepository
): NoteCommand {

    override fun createNote(noteCreateInput: NoteCreateInput):Long{
        return noteRepository.createNote(noteCreateInput)
    }

    override fun updateNote(noteUserUpdateInput: NoteUserUpdateInput) {
        noteRepository.updateNoteByUserIdAnCreatorId(noteUserUpdateInput)
    }


}