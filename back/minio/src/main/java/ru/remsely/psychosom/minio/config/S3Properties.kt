package ru.remsely.psychosom.minio.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "s3")
data class S3Properties(
    var internalEndpoint: String = "",
    var publicEndpoint: String = "",
    var region: String = "",
    var bucket: String = "",
    var accessKey: String = "",
    var secretKey: String = "",
    var pathStyleAccess: Boolean = false
)
