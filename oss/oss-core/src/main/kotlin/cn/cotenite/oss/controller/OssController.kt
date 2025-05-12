package cn.cotenite.oss.controller

import cn.cotenite.oss.service.MinioFileService
import cn.cotenite.response.Response
import jakarta.annotation.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 * @Author  RichardYoung
 * @Description
 * @Date  2025/3/28 00:05
 */
@RestController
@RequestMapping("/oss")
class OssController(
    private val minioFileService: MinioFileService
){

    @GetMapping("/uploadAvatar")
    fun uploadAvatar(file:MultipartFile,id:Long):Response {
        val url = minioFileService.uploadFile(file, "${id}/avatar")
        return Response.success(url)
    }

    @GetMapping("/uploadNotePicture")
    fun uploadNotePicture(file:MultipartFile,noteId:Long):Response {
        val url = minioFileService.uploadFile(file, "${noteId}/image")
        return Response.success(url)
    }

}