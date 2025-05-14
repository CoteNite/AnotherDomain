package cn.cotenite.note.repository.cache

import cn.cotenite.note.common.utils.RedisKeyCreator
import cn.cotenite.note.model.domain.NoteAgg
import cn.cotenite.note.model.entity.dto.NoteDetailView
import cn.hutool.core.util.RandomUtil
import com.github.benmanes.caffeine.cache.Caffeine
import org.redisson.api.RedissonClient
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Repository
import java.time.Duration
import java.util.concurrent.TimeUnit

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/15 02:59
 */
@Repository
class NoteAggCacheRepository(
    private val redissonClient: RedissonClient,
    private val threadPoolTaskExecutor: ThreadPoolTaskExecutor
){

    companion object {
        private val LOCAL_CACHE = Caffeine.newBuilder()
            .initialCapacity(10000)
            .maximumSize(10000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build<Long, NoteAgg>()
    }


    fun getNoteDetailCache(id: Long): NoteDetailView? {
        var agg = LOCAL_CACHE.getIfPresent(id)
        agg?.let { return it.createNoteDetailView() }

        val bucket = redissonClient.getBucket<NoteAgg>(RedisKeyCreator.noteDetailCacheKey(id))
        agg = bucket.get()?:return null
        threadPoolTaskExecutor.execute{
            LOCAL_CACHE.put(id, agg)
        }
        val view = agg.createNoteDetailView()
        return view
    }

    fun addNoteDetail2Cache(agg: NoteAgg){
        LOCAL_CACHE.put(agg.note.id, agg)
        val bucket = redissonClient.getBucket<NoteAgg>(RedisKeyCreator.noteDetailCacheKey(agg.note.id))
        bucket.set(agg, Duration.ofHours(1).plusSeconds(RandomUtil.randomLong(60)))
    }


}