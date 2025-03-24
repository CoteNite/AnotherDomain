package cn.cotenite.gateway.filter

import cn.cotenite.asp.Slf4j
import cn.cotenite.constants.GlobalConstants
import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException
import cn.cotenite.gateway.constants.RedisKeyConstants
import cn.dev33.satoken.stp.StpUtil
import cn.hutool.core.collection.CollUtil
import org.redisson.api.RedissonClient
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.Objects


/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/24 23:19
 */
@Slf4j
@Component
class AddUserId2HeaderFilter(
    val redissonClient: RedissonClient
):GlobalFilter{

    companion object{
        private const val TOKEN_HEADER_KEY: String = "Authorization"

        private const val TOKEN_HEADER_VALUE_PREFIX: String = "Bearer "
    }


    override fun filter(exchange: ServerWebExchange?, chain: GatewayFilterChain?): Mono<Void> {

        val tokenList = exchange!!.request.headers[TOKEN_HEADER_KEY]

        if (CollUtil.isEmpty(tokenList)) {
            return chain?.filter(exchange)?:throw BusinessException(Errors.SERVER_ERROR)
        }

        val tokenValue = tokenList!![0]

        val token = tokenValue.replace(TOKEN_HEADER_VALUE_PREFIX, "")


        val tokenRedisKey = RedisKeyConstants.SA_TOKEN_TOKEN_KEY_PREFIX + token

        val userId = redissonClient.getBucket<String>(tokenRedisKey).get()
            ?: return chain?.filter(exchange)
                ?:throw BusinessException(Errors.SERVER_ERROR)

        val newExchange = exchange.mutate().request { it.header(GlobalConstants.USER_ID, userId) }.build()

        return chain?.filter(newExchange)?:throw BusinessException(Errors.SERVER_ERROR)
    }
}