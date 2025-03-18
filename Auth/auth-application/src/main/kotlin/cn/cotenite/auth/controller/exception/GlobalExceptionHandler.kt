package cn.cotenite.auth.controller.exception

import cn.cotenite.asp.Slf4j
import cn.cotenite.asp.Slf4j.Companion.log
import cn.cotenite.expection.BusinessException
import cn.cotenite.response.Response
import cn.dev33.satoken.exception.SaTokenException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import java.util.*


/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/19 06:13
 */
@ControllerAdvice
@Slf4j
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    @ResponseBody
    fun handleBusinessException(request: HttpServletRequest,e: BusinessException): Response {
        printLog(request.requestURI,e.message)
        return Response.fail(e.message)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    fun handleMethodArgumentNotValidException(request: HttpServletRequest, e: MethodArgumentNotValidException): Response{
        val bindingResult = e.bindingResult
        val sb = StringBuilder()
        Optional.ofNullable(bindingResult.fieldErrors).ifPresent { errors ->
            errors.forEach { error ->
                sb.append(error.field)
                    .append(" ")
                    .append(error.defaultMessage)
                    .append(", 当前值: '")
                    .append(error.rejectedValue)
                    .append("'; ")
            }
        }
        val errorMessage = sb.toString()
        printLog(request.requestURI,e.message)
        return Response.fail(errorMessage)
    }

    @ExceptionHandler(SaTokenException::class)
    @ResponseBody
    fun handleSatokenException(request: HttpServletRequest,e: BusinessException): Response {
        printLog(request.requestURI,e.message)
        return Response.fail("登录/身份错误，请尝试退出并重新登录")
    }

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleOtherException(request: HttpServletRequest,e: BusinessException): Response {

        return Response.fail("系统出现未知错误，请联系网站管理员进行修复")
    }

    private fun printLog(url:String,message:String){
        log.warn("{} request error, errorMessage: {}", url, message)
    }

}