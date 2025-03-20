package cn.cotenite.auth.commons.utils

import cn.cotenite.expection.BusinessException
import cn.cotenite.id.api.IdGeneratorService
import org.apache.dubbo.config.annotation.DubboReference
import org.babyfish.jimmer.sql.meta.UserIdGenerator
import org.springframework.stereotype.Component

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/19 06:59
 */
@Component
class SnowflakeIdGenerator(
    @DubboReference(interfaceClass = IdGeneratorService::class, version = "1.0")
    private val idGeneratorService: IdGeneratorService
):UserIdGenerator<Long>{
    override fun generate(entityType: Class<*>?): Long {
        val id=idGeneratorService.getSegmentId("another-domain-segment-user-id")?:throw BusinessException("获取id失败")
        return id.toLong()
    }

    fun getId(): Long {
        val id=idGeneratorService.getSegmentId("another-domain-segment-user-id")?:throw BusinessException("获取id失败")
        return id.toLong()
    }

}