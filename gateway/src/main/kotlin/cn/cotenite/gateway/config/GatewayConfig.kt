package cn.cotenite.gateway.config

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.result.view.ViewResolver


/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/22 09:24
 */
@Configuration
@ComponentScan(value = ["cn.cotenite.*"])
@ServletComponentScan(basePackages = ["cn.cotenite.*"])
@EnableAspectJAutoProxy(proxyTargetClass = true)
class GatewayConfig(
    val viewResolvers:List<ViewResolver>,
    val serverCodecConfigurer: ServerCodecConfigurer,
    @Value("\${spring.cloud.gateway.discovery.locator.route-id-prefix}")
    var routeIdPrefix:String?
){

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun globalGatewayFilter():GlobalFilter{
        return SentinelGatewayFilter()
    }

    @PostConstruct
    fun init(){
        this.initGatewayRules()
        this.initBlockHandlers()
        this.initCustomizedApis()
    }

    private fun initGatewayRules(){
        val rules= HashSet<GatewayFlowRule>()
        rules.add(this.getGatewayFlowRule(this.getResource("auth")))
        //TODO 添加其余服务进入规则
        GatewayRuleManager.loadRules(rules)
    }


    private fun getGatewayFlowRule(resource:String):GatewayFlowRule{
        val gatewayFlowRule= GatewayFlowRule(resource)
        gatewayFlowRule.setCount(1.0)
        gatewayFlowRule.setIntervalSec(1)
        return gatewayFlowRule
    }


    private fun getResource(targetServiceName: String): String {
        if (routeIdPrefix == null){
            routeIdPrefix = "";
        }
        return routeIdPrefix+targetServiceName
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun sentinelGatewayBlockExceptionHandler(): SentinelGatewayBlockExceptionHandler {
        return SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer)
    }



    private fun initBlockHandlers() {
        val blockRequestHandler =BlockRequestHandler{ exchange, throwable ->
            val responseBody = mapOf<String, Any>(
                "code" to 1001,
                "codeMsg" to "Sentinel-接口被限流了"
            )

            ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(responseBody))

        }
        GatewayCallbackManager.setBlockHandler(blockRequestHandler)
    }

    private fun initCustomizedApis() {
        val definitions = mutableSetOf<ApiDefinition>().apply {
//            add(ApiDefinition("user_api1").apply {
//                predicateItems = mutableSetOf<ApiPredicateItem>().apply {
//                    add(ApiPathPredicateItem().apply {
//                        pattern = "/auth/auth/api1/**"
//                        matchStrategy = SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX
//                    })
//                }
//            })
//            add(ApiDefinition("user_api2").apply {
//                predicateItems = mutableSetOf<ApiPredicateItem>().apply {
//                    add(ApiPathPredicateItem().apply {
//                        pattern = "/auth/auth/api2/demo1"
//                    })
//                }
//            })
            //TODO 添加其余服务进入规则,产靠案例如上
        }
        GatewayApiDefinitionManager.loadApiDefinitions(definitions)
    }


}