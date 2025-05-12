package cn.cotenite.note.common.enums

import cn.cotenite.enums.CodeEnum
import org.babyfish.jimmer.sql.EnumType

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/13 05:49
 */
@EnumType(EnumType.Strategy.ORDINAL)
enum class Visible(
    override val code: Int
): CodeEnum {
    ALL_VISIBLE(0),
    ALL_FANS_VISIBLE(1),
    ONLY_ME_VISIBLE(2),
}