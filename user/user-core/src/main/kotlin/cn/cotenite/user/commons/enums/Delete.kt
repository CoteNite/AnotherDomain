package cn.cotenite.user.commons.enums

import org.babyfish.jimmer.sql.EnumType

/**
 * @Author  RichardYoung
 * @Description  
 * @Date  2025/3/19 02:18
*/
@EnumType(EnumType.Strategy.ORDINAL)
enum class Delete {
    UNDELETE,
    DELETED
}