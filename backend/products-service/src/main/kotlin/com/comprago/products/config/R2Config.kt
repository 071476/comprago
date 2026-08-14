package com.comprago.products.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import java.net.URI

@Configuration
class R2Config(
    @Value("\${r2.endpoint:}") private val endpoint: String,
    @Value("\${r2.access-key:}") private val accessKey: String,
    @Value("\${r2.secret-key:}") private val secretKey: String
) {
    @Bean
    fun s3Client(): S3Client? {
        if (endpoint.isBlank() || accessKey.isBlank() || secretKey.isBlank()) {
            return null
        }
        return S3Client.builder()
            .endpointOverride(URI.create(endpoint))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKey, secretKey)
                )
            )
            .region(Region.of("auto"))
            .forcePathStyle(true)
            .build()
    }
}
