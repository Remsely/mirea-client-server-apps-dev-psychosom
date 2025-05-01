package ru.remsely.psyhosom.domain.file

data class S3File(
    val bucket: String,
    val key: String,
    val url: String
)
