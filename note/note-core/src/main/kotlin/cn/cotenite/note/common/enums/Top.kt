package cn.cotenite.note.common.enums

import cn.cotenite.enums.CodeEnum
import org.babyfish.jimmer.sql.EnumType

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/13 05:51
 */
@EnumType(EnumType.Strategy.ORDINAL)
enum class Top(
    override val code: Int
): CodeEnum {

    UN_TOP(0),
    TOP(1);
}