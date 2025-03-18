package cn.cotenite.utils

import cn.hutool.core.util.IdUtil


/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/16 12:12
 */
object SnowFlakeGenerator{

    fun generate(): Long {
        val snowflake = IdUtil.getSnowflake(1, 1)
        return snowflake.nextId()
    }

}