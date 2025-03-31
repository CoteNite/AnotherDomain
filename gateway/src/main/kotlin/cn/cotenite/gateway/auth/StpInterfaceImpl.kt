package cn.cotenite.gateway.auth

import cn.cotenite.asp.Slf4j
import cn.cotenite.asp.Slf4j.Companion.log
import cn.cotenite.enums.YesOrNo
import cn.cotenite.gateway.commons.utils.RedisKeyCreator
import cn.dev33.satoken.stp.StpInterface
import com.github.benmanes.caffeine.cache.Caffeine
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/24 17:01
 */
@Component
@Slf4j
class StpInterfaceImpl(
    val redissonClient: RedissonClient
):StpInterface{

    companion object{
        val LOCAL_CACHE = Caffeine
            .newBuilder()
            .initialCapacity(10000)
            .maximumSize(10000)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build<Long,MutableMap<String,Int>>()

    }

    override fun getPermissionList(id: Any?, loginType: String?): MutableList<String> {
        val userId = (id as String).toLong()
        val map:MutableMap<String,Int>

        if (LOCAL_CACHE.getIfPresent(userId)!=null){
            map = LOCAL_CACHE.getIfPresent(userId)
        }else {
            val userPermissionHashKey = RedisKeyCreator.userPermissionHashKey(userId)
            val rMap = redissonClient.getMap<String, Int>(userPermissionHashKey)
            map = rMap.toMutableMap()
            LOCAL_CACHE.put(userId,map)
        }

        map.filter { (_,yesOrNo) ->
            yesOrNo==YesOrNo.YES.id
        }

        log.info("用户{}的权限为{}",userId,map.keys)
        return map.keys.toMutableList()
    }

    override fun getRoleList(p0: Any?, p1: String?): MutableList<String> {
        TODO("Not yet implemented")
    }
}