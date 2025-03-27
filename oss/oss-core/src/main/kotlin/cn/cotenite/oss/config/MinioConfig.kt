package cn.cotenite.oss.config

import io.minio.MinioClient
import jakarta.annotation.Resource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component


/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/27 09:37
 */
@Configuration
class MinioConfig(
    private val minioProperties: MinioProperties
){
    @Bean
    fun minioClient(): MinioClient {
        return MinioClient.builder()
            .endpoint(minioProperties.endpoint)
            .credentials(minioProperties.accessKey, minioProperties.secretKey)
            .build()
    }
}






@ConfigurationProperties(prefix ="minio")
@Component
data class MinioProperties(
    var endpoint:String="",
    var accessKey:String="",
    var secretKey:String=""
)