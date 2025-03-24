package cn.cotenite.response

import cn.cotenite.enums.Errors
import cn.cotenite.expection.BusinessException

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/15 03:24
 */
data class Response(
    val code:Int,
    val msg:String,
    val data:Any?
){
    companion object {
        fun success(data:Any?): Response {
            return Response(200,"success",data)
        }

        fun success(): Response {
            return Response(200,"success",null)
        }

        fun fail(msg:String): Response {
            return Response(400,msg,null)
        }

        fun fail(err: BusinessException): Response {
            return Response(400,err.message,null)
        }

        fun fail(code: Int, msg: String): Response {
            return Response(code,msg,null)
        }

        fun fail(retryLater: Errors): Response {
            return Response(200,retryLater.message,null)
        }

        fun fail(code: String, msg: String): Response {
            return Response(code.toInt(),msg,null)
        }


    }
}
