package cn.cotenite.kv.query

import cn.cotenite.api.query.NoteContentQuery
import cn.cotenite.kv.repository.NoteContentRepository
import org.apache.dubbo.config.annotation.DubboService
import org.springframework.web.bind.annotation.CrossOrigin
import java.util.*

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/29 20:50
 */
@CrossOrigin("*")
@DubboService(version = "1.0")
class NoteContentQueryImpl(
    private val noteContentRepository: NoteContentRepository
):NoteContentQuery{
    override fun queryNoteContent(noteId: UUID): String? {
        return noteContentRepository.findContentById(noteId)
    }
}