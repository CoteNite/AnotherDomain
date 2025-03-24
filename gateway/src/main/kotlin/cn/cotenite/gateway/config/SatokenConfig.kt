package cn.cotenite.gateway.config

import cn.dev33.satoken.exception.NotLoginException
import cn.dev33.satoken.exception.NotPermissionException
import cn.dev33.satoken.exception.NotRoleException
import cn.dev33.satoken.reactor.filter.SaReactorFilter
import cn.dev33.satoken.router.SaRouter
import cn.dev33.satoken.stp.StpUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/24 17:13
 */
@Configuration
class SatokenConfig {

    @Bean
    fun getSaReactorFilter(): SaReactorFilter {
        return SaReactorFilter()
            .addInclude("/**")
            .setAuth {
                SaRouter.match("/**")
                    .notMatch("/auth/auth/login",
                        "/auth/auth/register",
                        "/auth/auth/restPassword",
                        "/auth/auth/registerCode",
                        "/auth/auth/resetPasswordCode",
                        "/auth/auth/loginCode")
                    .check(StpUtil::checkLogin)
            }
            .setError {
                when(it){
                    is NotLoginException-> throw NotLoginException(it.message, null, null)
                    is NotPermissionException,is NotRoleException -> throw NotPermissionException(it.message)
                    else -> throw RuntimeException(it.message)
                }
            }

    }

}