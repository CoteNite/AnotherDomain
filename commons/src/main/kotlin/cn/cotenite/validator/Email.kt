package cn.cotenite.validator


import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/19 02:41
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.TYPE, AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [EmailValidator::class])
annotation class Email(val message: String = "手机号格式不正确, 需为 11 位数字",
                       val groups: Array<KClass<*>> = [],
                       val payload: Array<KClass<out Payload>> = [])