package cn.cotenite.auth.controller.exception

import cn.cotenite.asp.Slf4j
import cn.cotenite.asp.Slf4j.Companion.log
import cn.cotenite.expection.BusinessException
import cn.cotenite.response.Response
import cn.dev33.satoken.exception.SaTokenException
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException
import com.alibaba.csp.sentinel.slots.block.flow.FlowException
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException
import com.alibaba.csp.sentinel.slots.system.SystemBlockException
import com.alibaba.nacos.api.model.v2.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import java.lang.reflect.UndeclaredThrowableException
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
    fun handleBusinessException(e: BusinessException,request: HttpServletRequest): Response {
        printLog(request.requestURI,e.message)
        return Response.fail(e.message)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    fun handleMethodArgumentNotValidException( e: MethodArgumentNotValidException,request: HttpServletRequest): Response{
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
        printLog(request.requestURI,e.message?:"Validation错误")
        log.error("",e)
        return Response.fail(errorMessage)
    }

    @ExceptionHandler(SaTokenException::class)
    @ResponseBody
    fun handleSatokenException(e: SaTokenException,request: HttpServletRequest): Response {
        printLog(request.requestURI,e.message?:"Satoken错误")
        log.error("",e)
        return Response.fail("登录/身份错误，请尝试退出并重新登录")
    }

    @ExceptionHandler(FlowException::class)
    @ResponseBody
    fun handleUndeclaredThrowableException(e: FlowException, request: HttpServletRequest): Response{
        log.error("",e)
        return Response.fail(10001,"请求次数过多，请过段时间再来尝试吧")
    }


    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleOtherException(e: Exception, request: HttpServletRequest): Response {
        printLog("错误类为：："+e::class.toString(),request.requestURI,e.message?:"未知错误")
        log.error("",e)
        return Response.fail("系统出现未知错误，请联系网站管理员进行修复")
    }

    private fun printLog(url:String,message:String){
        log.error("{} request error, errorMessage: {}", url, message)
    }

    private fun printLog(prefix:String,url:String,message:String){
        log.error("{}——{} request error, errorMessage: {}", prefix,url, message)
    }

}