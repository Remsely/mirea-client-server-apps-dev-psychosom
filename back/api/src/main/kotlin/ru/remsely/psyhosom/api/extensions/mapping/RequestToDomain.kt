package ru.remsely.psyhosom.api.extensions.mapping

import org.springframework.web.multipart.MultipartFile
import ru.remsely.psyhosom.api.dto.ArticleBlockDto
import ru.remsely.psyhosom.domain.file.UploadedFile
import ru.remsely.psyhosom.domain.psychologist.Article

fun MultipartFile.toDomain(): UploadedFile = UploadedFile(
    originalName = this.originalFilename ?: "file",
    contentType = this.contentType ?: "application/octet-stream",
    size = this.size,
    streamProvider = { this.inputStream }
)

fun List<ArticleBlockDto>.toDomain(): Article =
    Article(this.map { it.toDomain() })

fun ArticleBlockDto.toDomain(): Article.ArticleBlock = Article.ArticleBlock(
    type = type,
    content = content,
)
