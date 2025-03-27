package cn.cotenite.oss.service

import cn.cotenite.expection.BusinessException
import cn.cotenite.oss.config.MinioProperties
import io.minio.MinioClient
import io.minio.PutObjectArgs
import jakarta.annotation.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/27 09:51
 */
@Service
class MinioFileService(
    @Resource
    private val minioClient: MinioClient,
    @Resource
    private val minioProperties: MinioProperties,
){


    companion object {
        const val BUCKET_NAME_PREFIX: String = "anotherDomain/"
    }


    fun uploadFile(file: MultipartFile, bucketName: String): String {
        if (file.size==0L){
            throw BusinessException("文件为空")
        }

        val originalFilename = file.originalFilename
        val contentType = file.contentType

        val key = UUID.randomUUID().toString().replace("-","")
        val suffix = originalFilename!!.substring(originalFilename.lastIndexOf("."))
        val objectName = java.lang.String.format("%s%s", key, suffix)

        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(BUCKET_NAME_PREFIX+bucketName)
                .`object`(objectName)
                .contentType(contentType)
                .stream(file.inputStream,file.size,file.size)
                .build()
        )

        return String.format("%s/%s/%s", minioProperties.endpoint, bucketName, objectName)
    }


}