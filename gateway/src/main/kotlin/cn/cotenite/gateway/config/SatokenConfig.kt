package cn.cotenite.gateway.config

import cn.cotenite.response.Response
import cn.dev33.satoken.reactor.filter.SaReactorFilter
import cn.dev33.satoken.router.SaRouter
import cn.dev33.satoken.router.SaRouterStaff
import cn.dev33.satoken.stp.StpUtil
import cn.dev33.satoken.util.SaResult
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

    }

}