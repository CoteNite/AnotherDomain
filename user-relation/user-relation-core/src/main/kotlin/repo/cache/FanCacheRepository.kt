package cn.cotenite.repo.cache

import cn.cotenite.model.domain.Fan
import com.github.benmanes.caffeine.cache.Caffeine
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/17 23:23
 */
@Repository
class FanCacheRepository(
    private val redissonClient: RedissonClient
){
    companion object{
        private val LOCAL_CACHE = Caffeine.newBuilder()
            .initialCapacity(10000)
            .maximumSize(10000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build<Long, Fan>()
    }
}