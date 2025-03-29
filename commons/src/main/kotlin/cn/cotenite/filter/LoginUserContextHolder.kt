package cn.cotenite.filter

import cn.cotenite.constants.GlobalConstants
import com.alibaba.ttl.TransmittableThreadLocal
import org.apache.dubbo.rpc.RpcContext


/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/24 23:49
 */
class LoginUserContextHolder {

    companion object {
        private val LOGIN_USER_CONTEXT_THREAD_LOCAL: TransmittableThreadLocal<MutableMap<String, Any?>> = TransmittableThreadLocal.withInitial(::HashMap)

        fun setUserId(value: Any?) {
            LOGIN_USER_CONTEXT_THREAD_LOCAL.get()[GlobalConstants.USER_ID] = value
        }

        fun getUserId(): Long {
            val value = LOGIN_USER_CONTEXT_THREAD_LOCAL.get()[GlobalConstants.USER_ID].let { RpcContext.getContext().getAttachment(GlobalConstants.USER_ID) }
            return value.toLong()
        }

        fun remove() {
            LOGIN_USER_CONTEXT_THREAD_LOCAL.remove()
        }
    }


}