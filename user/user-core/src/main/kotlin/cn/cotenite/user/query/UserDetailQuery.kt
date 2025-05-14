package cn.cotenite.user.query

import cn.cotenite.user.model.domain.dto.UserDetailSimpleView
import cn.cotenite.user.model.domain.dto.UserDetailView
import cn.cotenite.user.repository.cache.UserDetailViewCacheRepository
import cn.cotenite.user.repository.database.UserDetailRepository
import cn.cotenite.utils.UserIdContextHolder
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/27 07:49
 */
interface UserDetailQuery {
    fun getUserDetailAll(): UserDetailView
    fun getUserDetailSimpleView(userId: Long) :UserDetailSimpleView
}

@Service
class UserDetailQueryImpl(
    private val userDetailRepository: UserDetailRepository,
    private val userDetailViewCacheRepository: UserDetailViewCacheRepository,
    private val threadPoolTaskExecutor: ThreadPoolTaskExecutor
):UserDetailQuery{

    override fun getUserDetailAll(): UserDetailView {
        val id = UserIdContextHolder.getId()
        return userDetailRepository.getUserDetailAll(id)
    }

    override fun getUserDetailSimpleView(userId: Long): UserDetailSimpleView {
        var view = userDetailViewCacheRepository.getUserDetailSimpleView(userId)
        if (view==null){
            view = userDetailRepository.getUserDetailSimpleView(userId)
            threadPoolTaskExecutor.execute {
                userDetailViewCacheRepository.addUserDetailSimple2Cache(view)
            }
        }
        return view
    }


}