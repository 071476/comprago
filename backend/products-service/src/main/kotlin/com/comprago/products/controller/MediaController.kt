package com.comprago.products.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.UUID

@RestController
@RequestMapping("/api/media")
class MediaController(private val s3Client: S3Client?) {

    private val bucket = "comprago-media"
    private val publicUrl = "https://pub-6e8559655f8a4f13b3f549c025413f4c.r2.dev"

    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<Map<String, String>> {
        if (s3Client == null) {
            return ResponseEntity.status(503).body(mapOf("error" to "File upload not configured"))
        }

        val fileName = "${UUID.randomUUID()}-${file.originalFilename}"

        val request = PutObjectRequest.builder()
            .bucket(bucket)
            .key(fileName)
            .contentType(file.contentType)
            .build()

        s3Client.putObject(request, RequestBody.fromInputStream(file.inputStream, file.size))
        val url = "$publicUrl/$fileName"
        return ResponseEntity.ok(mapOf("url" to url, "fileName" to fileName))
    }
}
