package cn.cotenite.note.repository.cache

import cn.cotenite.asp.Slf4j
import cn.cotenite.asp.Slf4j.Companion.log
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
@Slf4j
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
        if (agg != null) {
            log.debug("命中本地缓存, id: {}", id)
            return agg.createNoteDetailView()
        }

        val bucket = redissonClient.getBucket<NoteAgg?>(RedisKeyCreator.noteDetailCacheKey(id))
        try {
            agg = bucket.get()
        } catch (e: Exception) {
            log.error("从 Redis 获取缓存失败, id: {}", id, e)
            return null
        }

        if (agg == null) {
            log.debug("Redis 缓存未命中或为 null, id: {}", id)
            threadPoolTaskExecutor.execute {
                try {
                    val expirationSeconds = 60 + RandomUtil.randomLong(60)
                    bucket.set(null, Duration.ofSeconds(expirationSeconds))
                    log.debug("为不存在的 key 设置短时效 null 值成功, id: {}, 过期时间: {}s", id, expirationSeconds)
                } catch (e: Exception) {
                    log.error("为不存在的 key 设置短时效 null 值失败, id: {}", id, e)
                }
            }
            return null
        }

        log.debug("Redis 缓存命中, id: {}", id)
        threadPoolTaskExecutor.execute {
            try {
                LOCAL_CACHE.put(id, agg)
                log.debug("异步更新本地缓存成功, id: {}", id)
            } catch (e: Exception) {
                log.error("异步更新本地缓存失败, id: {}", id, e)
            }
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