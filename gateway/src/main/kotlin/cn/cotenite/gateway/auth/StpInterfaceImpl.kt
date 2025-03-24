package cn.cotenite.gateway.auth

import cn.cotenite.asp.Slf4j
import cn.cotenite.asp.Slf4j.Companion.log
import cn.cotenite.commons.YesOrNo
import cn.cotenite.gateway.commons.utils.RedisKeyCreator
import cn.dev33.satoken.stp.StpInterface
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component

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
    override fun getPermissionList(id: Any?, loginType: String?): MutableList<String> {
        val userId = id as String
        val userPermissionHashKey = RedisKeyCreator.userPermissionHashKey(userId.toLong())
        val rMap = redissonClient.getMap<String, Int>(userPermissionHashKey)
        val map = rMap.toMutableMap()
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