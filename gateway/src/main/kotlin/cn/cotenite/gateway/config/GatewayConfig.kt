package cn.cotenite.gateway.config

import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/22 09:24
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
class GatewayConfig {
}