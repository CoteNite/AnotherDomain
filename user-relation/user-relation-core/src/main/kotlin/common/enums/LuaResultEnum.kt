package cn.cotenite.common.enums

import cn.cotenite.enums.CodeEnum

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/18 04:57
 */
enum class LuaResultEnum(
    override val code: Int
):CodeEnum {

    // ZSET 不存在
    ZSET_NOT_EXIST(-1),
    // 关注已达到上限
    FOLLOW_LIMIT(-2),
    // 已经关注了该用户
    ALREADY_FOLLOWED(-3),
    // 关注成功
    FOLLOW_SUCCESS(0),
    ;

}