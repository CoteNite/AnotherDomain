package cn.cotenite.user.query

import cn.cotenite.user.repository.cache.UserDetailViewCacheRepository
import cn.cotenite.user.repository.database.UserDetailRepository
import org.apache.dubbo.config.annotation.DubboService
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.bind.annotation.CrossOrigin

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/18 01:28
 */
@CrossOrigin("*")
@DubboService(version = "1.0")
class UserDetailDubboQueryImpl(
    private val userDetailRepository: UserDetailRepository,
    private val cacheRepository: UserDetailViewCacheRepository,
    private val threadPoolTaskExecutor: ThreadPoolTaskExecutor
):UserDetailDubboQuery{

    override fun checkUserIdExist(id: Long): Boolean {
        if (cacheRepository.checkUserExistInCache(id)) return true
        threadPoolTaskExecutor.execute {
            cacheRepository.addUserId2CacheSet(id)
        }
        return userDetailRepository.checkUserDetailExist(id)
    }
}