package cn.cotenite.note.query

import cn.cotenite.note.model.domain.NoteAgg
import cn.cotenite.note.model.entity.dto.NoteDetailView
import cn.cotenite.note.repository.cassandra.NoteContentRepository
import cn.cotenite.note.repository.database.NoteRepository
import cn.cotenite.note.repository.cache.NoteAggCacheRepository
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import java.util.*

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/15 01:52
 */
interface NoteQuery {
    fun find4DetailById(id: Long): NoteDetailView
}

@Service
class NoteQueryImpl(
    val noteRepository: NoteRepository,
    val noteContentRepository: NoteContentRepository,
    val noteAggCacheRepository: NoteAggCacheRepository,
    val threadPoolTaskExecutor: ThreadPoolTaskExecutor
) : NoteQuery {

    override fun find4DetailById(id: Long): NoteDetailView {

        var view = noteAggCacheRepository.getNoteDetailCache(id)
        view?.let { return it }

        view = noteAggCacheRepository.getNoteDetailCache(id)
        view?.let {
            return it
        }

        val note = noteRepository.selectOneById(id)
        val noteAgg = NoteAgg(note, null)
        note.contentUuid?.let {
            noteAgg.noteContent=noteContentRepository.findById(UUID.fromString(it)).get().content
        }

        threadPoolTaskExecutor.execute {
            noteAggCacheRepository.addNoteDetail2Cache(noteAgg)
        }

        return noteAgg.createNoteDetailView()
    }

}