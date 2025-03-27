package cn.cotenite.user.commons.enums

import org.babyfish.jimmer.sql.EnumType

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/28 02:25
 */
@EnumType(EnumType.Strategy.ORDINAL)
enum class Gender{
    MALE,
    FEMALE
}