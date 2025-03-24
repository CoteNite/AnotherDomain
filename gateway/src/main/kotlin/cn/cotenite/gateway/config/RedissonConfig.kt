package cn.cotenite.gateway.config

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.codec.JacksonCodec
import org.redisson.codec.JsonJacksonCodec
import org.redisson.config.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/15 04:52
 */
@Configuration
class RedissonConfig{
    @Bean
    fun redissonClient(resourceLoader: ResourceLoader):RedissonClient{
        val resource = resourceLoader.getResource("classpath:redisson.yml")
        val config:Config = Config.fromYAML(resource.inputStream)
        config.codec= JsonJacksonCodec().apply {
            objectMapper.registerModule(
                KotlinModule.Builder()
                    .withReflectionCacheSize(512)
                    .configure(KotlinFeature.NullToEmptyCollection, false)
                    .configure(KotlinFeature.NullToEmptyMap, false)
                    .configure(KotlinFeature.NullIsSameAsDefault, false)
                    .configure(KotlinFeature.SingletonSupport, false)
                    .configure(KotlinFeature.StrictNullChecks, false)
                    .build()
            )
        }
        return Redisson.create(config)
    }

}