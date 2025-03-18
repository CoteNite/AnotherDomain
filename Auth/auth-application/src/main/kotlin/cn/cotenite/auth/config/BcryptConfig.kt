package cn.cotenite.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/16 11:56
 */
@Configuration
class BcryptConfig {

    @Bean
    fun PasswordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}