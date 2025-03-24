package cn.cotenite.filter

import cn.cotenite.asp.Slf4j
import cn.cotenite.asp.Slf4j.Companion.log
import cn.cotenite.constants.GlobalConstants
import cn.hutool.core.util.StrUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/24 23:44
 */
@Slf4j
@Component
class HeaderUserId2ContextFilter:OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ){
        val userId = request.getHeader(GlobalConstants.USER_ID)

        if (StrUtil.isBlank(userId)){
            chain.doFilter(request,response)
            return
        }

        LoginUserContextHolder.setUserId(userId)

        try {
            chain.doFilter(request,response)
        }finally {
            LoginUserContextHolder.remove()
            log.info("===== 删除 ThreadLocal， userId: {}", userId)
        }

    }
}