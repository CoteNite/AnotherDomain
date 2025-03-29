package cn.cotenite.filter

import cn.cotenite.constants.GlobalConstants
import org.apache.dubbo.common.constants.CommonConstants
import org.apache.dubbo.common.extension.Activate
import org.apache.dubbo.rpc.Filter
import org.apache.dubbo.rpc.Invocation
import org.apache.dubbo.rpc.Invoker
import org.apache.dubbo.rpc.Result
import org.apache.dubbo.rpc.RpcContext
import org.springframework.stereotype.Component

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/29 18:54
 */
@Component
@Activate(group = [CommonConstants.CONSUMER])
class HeaderFilter :Filter{
    override fun invoke(invoker: Invoker<*>?, invocation: Invocation?): Result {
        val userId = RpcContext.getContext().getAttachment(GlobalConstants.USER_ID)
        if (userId != null) {
            RpcContext.getContext().setAttachment(GlobalConstants.USER_ID,LoginUserContextHolder.getUserId())
        }
        return invoker?.invoke(invocation)!!
    }
}