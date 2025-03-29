package cn.cotenite.note.command

import cn.cotenite.api.command.NoteContentCommand
import cn.cotenite.note.models.dto.NoteDetailCreateInput
import cn.cotenite.note.repo.NoteDetailRepository
import io.seata.spring.annotation.GlobalTransactional
import org.apache.dubbo.config.annotation.DubboReference
import org.springframework.stereotype.Service

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/30 05:55
 */
interface NoteDetailCommand {
    fun createNoteDetail( noteDetailCreateInput: NoteDetailCreateInput)

}

@Service
class NoteDetailCommandImpl(
    private val noteDetailRepository: NoteDetailRepository
): NoteDetailCommand {

    override fun createNoteDetail(noteDetailCreateInput: NoteDetailCreateInput) {
        noteDetailRepository.createNoteDetail(noteDetailCreateInput)
    }

}
