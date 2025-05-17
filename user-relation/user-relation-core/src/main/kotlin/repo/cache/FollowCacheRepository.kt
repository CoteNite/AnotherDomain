package cn.cotenite.repo.cache

import cn.cotenite.asp.Slf4j
import cn.cotenite.asp.Slf4j.Companion.log
import cn.cotenite.common.enums.LuaResultEnum
import cn.cotenite.enums.Errors
import cn.cotenite.enums.getEntityByCode
import cn.cotenite.expection.BusinessException
import cn.cotenite.model.domain.Follow
import cn.cotenite.model.domain.dto.FollowView
import cn.cotenite.note.common.utils.RedisKeyCreator
import cn.cotenite.utils.DateUtil
import cn.cotenite.utils.UserIdContextHolder
import cn.hutool.core.util.RandomUtil
import com.github.benmanes.caffeine.cache.Caffeine
import org.redisson.api.RScript
import org.redisson.api.RedissonClient
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Repository
import org.springframework.util.FileCopyUtils
import java.io.IOException
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit


/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/17 23:25
 */
@Slf4j
@Repository
class FollowCacheRepository(
    private val redissonClient: RedissonClient,
    private val resourceLoader:ResourceLoader

){
    companion object {
        private val LOCAL_CACHE = Caffeine.newBuilder()
            .initialCapacity(10000)
            .maximumSize(10000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build<Long, Follow>()
    }

    fun checkFollowedInFollowingUserSet(followedUserId:Long):Long{
        val script = redissonClient.script

        val now = LocalDateTime.now()
        val timestamp: Long = DateUtil.localDateTime2Timestamp(now)

        val args = mutableListOf<Any>(followedUserId,timestamp)
        val keys= mutableListOf<Any>(RedisKeyCreator.buildFollowSetRedisKey(UserIdContextHolder.getId()))

        val result= script.eval<Long>(
            RScript.Mode.READ_WRITE,
            this.loadLuaScript("follow_check_and_add.lua"),
            RScript.ReturnType.INTEGER,
            keys,
            args
        )
        return result
    }

    fun addBatch2FollowingUserSet(followedUserIdList:List<FollowView>){
        val script = redissonClient.script

        val expireSeconds=60*24*24+RandomUtil.randomInt(60*60*24)
        val keys= mutableListOf<Any>(RedisKeyCreator.buildFollowSetRedisKey(UserIdContextHolder.getId()))
        val args = this.buildLuaArgs(followedUserIdList,expireSeconds)

       script.eval<Long>(
           RScript.Mode.READ_WRITE,
           this.loadLuaScript("follow_batch_add_and_expire.lua"),
           RScript.ReturnType.INTEGER,
           keys,
           args
       )
    }

    private fun loadLuaScript(scriptName:String): String {
        val resource = resourceLoader.getResource("classpath:lua/${scriptName}")
        var context:String
        try {
            InputStreamReader(resource.inputStream).use { reader ->
                context = FileCopyUtils.copyToString(reader)
            }
        } catch (e: IOException) {
            log.error("Lua脚本加载失败",e)
            throw BusinessException(Errors.SERVER_ERROR)
        }
        return context
    }

    private fun buildLuaArgs(followingDOS: List<FollowView>, expireSeconds: Int): Array<Any?> {
        val argsLength = followingDOS.size * 2 + 1
        val luaArgs = arrayOfNulls<Any>(argsLength)

        followingDOS.withIndex().forEach { (index, following) ->
            val i = index * 2
            luaArgs[i] = DateUtil.localDateTime2Timestamp(following.updateTime)
            luaArgs[i + 1] = following.followingUserId
        }
        luaArgs[argsLength - 1] = expireSeconds
        return luaArgs
    }
}