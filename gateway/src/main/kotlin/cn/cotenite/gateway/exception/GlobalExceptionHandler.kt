package cn.cotenite.gateway.exception

import cn.cotenite.asp.Slf4j
import cn.cotenite.asp.Slf4j.Companion.log
import cn.cotenite.commons.Errors
import cn.cotenite.response.Response
import cn.dev33.satoken.exception.SaTokenException
import com.fasterxml.jackson.databind.ObjectMapper

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/24 19:23
 */
@Slf4j
@Component
class GlobalExceptionHandler(
    val objectMapper: ObjectMapper
):ErrorWebExceptionHandler{


    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val response = exchange.response

        log.error("==> 全局异常捕获: ", ex);

        val result: Response

        if (ex is SaTokenException) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED)

            result = Response.fail(Errors.UNAUTHORIZED)
        } else {
            result = Response.fail(Errors.SERVER_ERROR)
        }


        response.headers.contentType = MediaType.APPLICATION_JSON_UTF8

        return response.writeWith(Mono.fromSupplier {
            val bufferFactory = response.bufferFactory()
            try {
                return@fromSupplier bufferFactory.wrap(objectMapper.writeValueAsBytes(result))
            } catch (e: Exception) {
                return@fromSupplier bufferFactory.wrap(ByteArray(0))
            }
        })
    }


}