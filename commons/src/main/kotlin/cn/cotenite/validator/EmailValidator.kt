package cn.cotenite.validator

import org.springframework.stereotype.Component
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/19 02:41
 */
@Component
class EmailValidator : ConstraintValidator<Email,String>{
    override fun isValid(email: String?, p1: ConstraintValidatorContext?): Boolean {
        println(email)
        return email!=null&&email.matches("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}".toRegex())
    }
}