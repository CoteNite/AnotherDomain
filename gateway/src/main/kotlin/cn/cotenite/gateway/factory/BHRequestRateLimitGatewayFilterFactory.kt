package cn.cotenite.gateway.factory

import cn.cotenite.commons.Errors
import cn.cotenite.response.Response
import com.alibaba.fastjson.JSON
import com.google.common.cache.CacheBuilder
import com.google.common.util.concurrent.RateLimiter
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.Ordered
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/23 10:27
 */
@Component
class BHRequestRateLimitGatewayFilterFactory
    :AbstractGatewayFilterFactory<BHRequestRateLimitGatewayFilterFactory.Companion.Config>(Config::class.java)
{
    companion object {
        private val RATE_LIMITER_CACHE = CacheBuilder
            .newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(1, TimeUnit.HOURS)
            .build<String, RateLimiter>()

        class Config {
            private var permitsPerSecond: String = "1.0"

            fun getPermitsPerSecond(): String {
                return permitsPerSecond
            }

            fun setPermitsPerSecond(permitsPerSecond: String) {
                this.permitsPerSecond = permitsPerSecond
            }
        }
    }

    override fun shortcutFieldOrder(): List<String> {
        return Collections.singletonList("permitsPerSecond")
    }

    override fun name(): String {
        return "bHRequestRateLimitGateway"
    }

    override fun apply(config: Config): GatewayFilter {
        return object : GatewayFilter, Ordered{
            override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
                try {
                    val remoteAddr = Objects.requireNonNull(exchange.request.remoteAddress)?.address?.hostAddress

                    val rateLimiter = remoteAddr?.let {
                        RATE_LIMITER_CACHE.get(it) {
                            RateLimiter.create(config.getPermitsPerSecond().toDouble())
                        }
                    }

                    if (rateLimiter != null) {
                        if (rateLimiter.tryAcquire()) {
                            return chain.filter(exchange)
                        }
                    }

                    val response = exchange.response
                    response.statusCode = HttpStatus.TOO_MANY_REQUESTS
                    response.headers.add("Content-Type", "application/json;charset=UTF-8")

                    val responseMessage =Response.fail(Errors.SERVER_ERROR)
                    val responseStr = JSON.toJSONString(responseMessage)

                    val dataBuffer = response.bufferFactory().wrap(responseStr.toByteArray(StandardCharsets.UTF_8))
                    return response.writeWith(Mono.just(dataBuffer))
                } catch (e: Exception) {
                    throw e
                }
            }

            override fun getOrder(): Int {
                return -1
            }
        }
    }


}