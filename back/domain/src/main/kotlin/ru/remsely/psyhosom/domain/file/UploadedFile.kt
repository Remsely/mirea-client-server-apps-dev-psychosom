package ru.remsely.psyhosom.domain.file

import java.io.InputStream

data class UploadedFile(
    val originalName: String,
    val contentType: String,
    val size: Long,
    val streamProvider: () -> InputStream
)
