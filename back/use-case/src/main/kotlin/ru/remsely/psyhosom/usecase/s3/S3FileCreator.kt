package ru.remsely.psyhosom.usecase.s3

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.file.S3File
import ru.remsely.psyhosom.domain.file.UploadedFile

interface S3FileCreator {
    fun createFile(file: UploadedFile): Either<DomainError, S3File>
}

sealed class S3CreatorIntegrationError(override val message: String) : DomainError.IntegrationError {
    data class CanNotUploadFile(private val exceptionMessage: String) : S3CreatorIntegrationError(
        "Can not upload file to S3 bucket. Error: $exceptionMessage."
    )
}
