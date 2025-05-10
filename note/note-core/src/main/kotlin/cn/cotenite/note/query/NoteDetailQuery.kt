package cn.cotenite.note.query

import cn.cotenite.api.command.NoteContentCommand
import cn.cotenite.api.query.NoteContentQuery
import cn.cotenite.note.models.dto.NoteDetailTextInfo
import cn.cotenite.note.models.dto.NoteDetailVideoInfo
import cn.cotenite.note.models.dto.NoteViewItem
import cn.cotenite.note.repo.NoteDetailRepository
import cn.cotenite.page.BasePage
import cn.cotenite.utils.UserIdContextHolder
import org.apache.dubbo.config.annotation.DubboReference
import org.babyfish.jimmer.Page
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import java.util.UUID

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/4/1 15:30
 */
interface NoteDetailQuery {
    fun findTextNoteDetailInfo(noteId:Long): NoteDetailTextInfo

    fun findVideoNoteDetailInfo(noteId:Long): NoteDetailVideoInfo

    fun findNoteDetailViewList(page:BasePage): Page<NoteViewItem>
}

@Service
class NoteDetailQueryImpl(
    private val noteDetailRepository: NoteDetailRepository,
    @DubboReference(interfaceClass = NoteContentCommand::class,version = "1.0")
    private val noteContentQuery: NoteContentQuery,
    private val taskExecutor: ThreadPoolTaskExecutor
) : NoteDetailQuery{

    override fun findTextNoteDetailInfo(noteId: Long): NoteDetailTextInfo {



        var noteDetail = noteDetailRepository.findTextNoteDetailInfoById(noteId)
        val content = noteContentQuery.queryNoteContent(UUID.fromString(noteDetail.contentUUID))

        noteDetail = NoteDetailTextInfo(
            id = noteDetail.id,
            creatorId = noteDetail.creatorId,
            title = noteDetail.title,
            contentUUID = noteDetail.contentUUID,
            content = content,
            imgUris = noteDetail.imgUris,
            updateTime = noteDetail.updateTime
        )
        return noteDetail
    }

    override fun findVideoNoteDetailInfo(noteId: Long): NoteDetailVideoInfo {
        var noteDetail = noteDetailRepository.findVideoNoteDetailInfoById(noteId)
        val content = noteContentQuery.queryNoteContent(UUID.fromString(noteDetail.contentUUID))
        noteDetail = NoteDetailVideoInfo(
            id = noteDetail.id,
            creatorId = noteDetail.creatorId,
            title = noteDetail.title,
            contentUUID = noteDetail.contentUUID,
            content = content,
            videoUri = noteDetail.videoUri,
            updateTime = noteDetail.updateTime
        )
        return noteDetail
    }

    override fun findNoteDetailViewList(page:BasePage): Page<NoteViewItem> {
        return noteDetailRepository.findNoteDetailViewList(page.pageIndex, page.pageSize)
    }


}