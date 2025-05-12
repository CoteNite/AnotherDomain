package cn.cotenite.note.common.enums

import cn.cotenite.enums.CodeEnum
import org.babyfish.jimmer.sql.EnumType

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/13 05:48
 */
@EnumType(EnumType.Strategy.ORDINAL)
enum class Status(
    override val code: Int
): CodeEnum {

    REVIEWING(0),
    REVIEWED(1),
    FAILED(2)

}