package cn.cotenite.enums

import cn.cotenite.expection.BusinessException
import kotlin.enums.enumEntries
import kotlin.reflect.KClass

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/5/13 07:32
 */
interface CodeEnum{
    val code: Int
}

inline fun <reified T> getEntityByCode(code: Int): T where T : Enum<T>, T : CodeEnum {
    return enumValues<T>().find { it.code == code }
        ?: throw BusinessException("${T::class.simpleName}不存在")
}