package cn.cotenite.command

import cn.cotenite.asp.Slf4j.Companion.log
import cn.cotenite.common.enums.LuaResultEnum
import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import cn.cotenite.repo.cache.FollowCacheRepository
import cn.cotenite.repo.database.FollowRepository
import cn.cotenite.user.query.UserDetailDubboQuery
import cn.cotenite.utils.UserIdContextHolder
import org.apache.dubbo.config.annotation.DubboReference
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/17 23:17
 */
interface FollowCommand {
    fun follow(followedUserId:Long)
}

@Service
class FollowCommandImpl(
    private val followRepository: FollowRepository,
    private val followCacheRepository: FollowCacheRepository,
    @DubboReference(interfaceClass = UserDetailDubboQuery::class, version = "1.0")
    private val userDetailDubboQuery: UserDetailDubboQuery,
    private val threadPoolTaskExecutor: ThreadPoolTaskExecutor
):FollowCommand{
    override fun follow(followedUserId: Long) {
        val userId = UserIdContextHolder.getId()
        if (userId == followedUserId){
            throw BusinessException("无法关注自己")
        }
        val userIdExist = userDetailDubboQuery.checkUserIdExist(followedUserId)
        if (!userIdExist){
            throw BusinessException(Errors.PARAM_VALIDATION_ERROR)
        }
        val result = followCacheRepository.checkFollowedInFollowingUserSet(followedUserId)


        when(result){
            LuaResultEnum.FOLLOW_LIMIT.code.toLong() -> {
                throw BusinessException("关注以达上限")
            }
            LuaResultEnum.ALREADY_FOLLOWED.code.toLong() -> {
                throw BusinessException("您已关注该用户")
            }
            LuaResultEnum.ZSET_NOT_EXIST.code.toLong() -> {
                followRepository.addFollow(userId,followedUserId)
                threadPoolTaskExecutor.execute{
                    val followingList = followRepository.getFollowingList(userId)
                    followCacheRepository.addBatch2FollowingUserSet(followingList)
                }
            }
            LuaResultEnum.FOLLOW_SUCCESS.code.toLong() -> {
                followRepository.addFollow(userId,followedUserId)
            }
            else -> {
                log.error("Lua脚本执行错误,返回值非法")
                throw BusinessException(Errors.SERVER_ERROR)
            }
        }
    }

}