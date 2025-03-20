package cn.cotenite.auth.commons.utils

import cn.cotenite.utils.SnowFlakeUtils
import org.babyfish.jimmer.sql.meta.UserIdGenerator

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/19 06:59
 */

class SnowflakeIdGenerator: UserIdGenerator<Long> {
    override fun generate(entityType: Class<*>?): Long {
        return SnowFlakeUtils.generate()
    }

}