package cn.cotenite.note.common.enums

import cn.cotenite.enums.CodeEnum
import cn.cotenite.expection.BusinessException
import org.babyfish.jimmer.sql.EnumType

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/13 05:47
 */
@EnumType(EnumType.Strategy.ORDINAL)
enum class Type(
    override val code: Int
): CodeEnum {
    IMAGE_CONTENT(0),
    VIDEO_CONTENT(1);


}