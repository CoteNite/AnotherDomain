package cn.cotenite.user.repository.cache

import cn.cotenite.user.commons.utils.RedisKeyCreator
import cn.cotenite.user.model.domain.dto.UserDetailSimpleView
import cn.cotenite.user.model.domain.dto.UserDetailView
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
 * @Date  2025/5/15 06:34
 */
@Repository
class UserDetailViewCacheRepository(
    private val redissonClient: RedissonClient,
    private val threadPoolTaskExecutor: ThreadPoolTaskExecutor
) {

    companion object{
        val LOCAL_CACHE=Caffeine.newBuilder()
            .initialCapacity(10000)
            .maximumSize(10000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build<Long, UserDetailSimpleView>()
    }

    fun getUserDetailSimpleView(userId: Long): UserDetailSimpleView? {
        val key = RedisKeyCreator.buildNoteDetailSimpleViewKey(userId)
        var simpleView = LOCAL_CACHE.getIfPresent(userId)
        if (simpleView != null) {
            return simpleView
        }
        val bucket = redissonClient.getBucket<UserDetailSimpleView>(key)
        simpleView = bucket.get()
        if (simpleView==null){
            bucket.set(null,Duration.ofSeconds(60+RandomUtil.randomLong(60)))
            return null
        }
        threadPoolTaskExecutor.execute{
            LOCAL_CACHE.put(userId,simpleView)
        }
        return simpleView
    }

    fun addUserDetailSimple2Cache(view:UserDetailSimpleView){
        val key = RedisKeyCreator.buildNoteDetailSimpleViewKey(view.id)
        val bucket = redissonClient.getBucket<UserDetailSimpleView>(key)
        bucket.set(view,Duration.ofDays(1).plusHours(RandomUtil.randomLong(24)))
        LOCAL_CACHE.put(view.id,view)
    }


}