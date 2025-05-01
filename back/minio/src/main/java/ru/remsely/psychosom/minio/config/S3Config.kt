package ru.remsely.psychosom.minio.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration
import java.net.URI

@Configuration
open class S3Config(
    private val properties: S3Properties
) {
    @Bean
    open fun s3Client(): S3Client = S3Client.builder()
        .endpointOverride(URI(properties.internalEndpoint))
        .region(Region.of(properties.region))
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsBasicCredentials.create(properties.accessKey, properties.secretKey)
            )
        )
        .serviceConfiguration(
            S3Configuration.builder().pathStyleAccessEnabled(properties.pathStyleAccess).build()
        )
        .build()
}
