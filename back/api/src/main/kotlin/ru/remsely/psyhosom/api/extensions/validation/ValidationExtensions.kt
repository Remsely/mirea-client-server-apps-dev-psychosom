package ru.remsely.psyhosom.api.extensions.validation

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import ru.remsely.psyhosom.api.dto.request.AddPsychologistEducationRequest
import ru.remsely.psyhosom.api.dto.request.CreateConsultationRequest
import ru.remsely.psyhosom.domain.error.DomainError
import java.time.LocalDateTime

fun CreateConsultationRequest.validate(): Either<DomainError.ValidationError, CreateConsultationRequest> =
    either {
        ensure(startDtTm.isAfter(LocalDateTime.now())) {
            CreateConsultationRequestValidationError.StartInThePast
        }
        this@validate
    }

fun AddPsychologistEducationRequest.validate(): Either<DomainError.ValidationError, AddPsychologistEducationRequest> =
    either {
        ensure(files.isNotEmpty()) {
            AddPsychologistEducationRequestValidationError.EmptyFileList
        }
        ensure(files.size < 5) {
            AddPsychologistEducationRequestValidationError.MaxFilesNumberLimitExceed
        }
        this@validate
    }

sealed class CreateConsultationRequestValidationError(
    override val message: String
) : DomainError.ValidationError {

    data object StartInThePast : CreateConsultationRequestValidationError(
        "StartDtTm must be in future."
    )
}

sealed class AddPsychologistEducationRequestValidationError(
    override val message: String
) : DomainError.ValidationError {

    data object EmptyFileList : CreateConsultationRequestValidationError(
        "No files were uploaded."
    )

    data object MaxFilesNumberLimitExceed : CreateConsultationRequestValidationError(
        "More than 5 files were uploaded. Please, upload 5 files or less."
    )
}
