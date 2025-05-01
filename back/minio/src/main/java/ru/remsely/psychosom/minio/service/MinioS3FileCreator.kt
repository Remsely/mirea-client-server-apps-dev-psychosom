package ru.remsely.psychosom.minio.service

import arrow.core.Either
import arrow.core.right
import org.springframework.stereotype.Component
import ru.remsely.psychosom.minio.config.S3Properties
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.file.S3File
import ru.remsely.psyhosom.domain.file.UploadedFile
import ru.remsely.psyhosom.usecase.s3.S3CreatorIntegrationError
import ru.remsely.psyhosom.usecase.s3.S3FileCreator
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.*

@Component
class MinioS3FileCreator(
    private val s3properties: S3Properties,
    private val s3Client: S3Client
) : S3FileCreator {
    override fun createFile(file: UploadedFile): Either<DomainError, S3File> =
        Either.catch {
            val key = "docs/${UUID.randomUUID()}_${file.originalName}"

            val request = PutObjectRequest.builder()
                .bucket(s3properties.bucket)
                .key(key)
                .contentType(file.contentType)
                .contentLength(file.size)
                .build()

            file.streamProvider().use { input ->
                s3Client.putObject(request, RequestBody.fromInputStream(input, file.size))
            }

            val url = "${s3properties.publicEndpoint}/${s3properties.bucket}/$key"

            return S3File(
                bucket = s3properties.bucket,
                key = key,
                url = url
            ).right()

        }.mapLeft { S3CreatorIntegrationError.CanNotUploadFile(it.message ?: it.javaClass.name) }
}
