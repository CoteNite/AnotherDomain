package cn.cotenite.note.common.enums

import org.babyfish.jimmer.sql.EnumType

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/13 05:46
 */
@EnumType(EnumType.Strategy.ORDINAL)
enum class Delete(
    code:Int
){
    UNDELETE(0),
    DELETED(1)
}