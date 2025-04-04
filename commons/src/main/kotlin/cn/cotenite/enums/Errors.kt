package cn.cotenite.enums


/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/23 07:00
 */
enum class Errors(
    val message:String
){

    RETRY_LATER(message = "请求太频繁，请稍后再试"),
    SERVER_ERROR(message = "服务器异常，请联系网站管理员修复"),
    UNAUTHORIZED(message = "权限不足"),
    PARAM_VALIDATION_ERROR(message = "输入的信息有误，请重试")
    ;



}