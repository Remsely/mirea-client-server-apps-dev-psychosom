package ru.remsely.psyhosom.domain.consultation

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.patient.Patient
import ru.remsely.psyhosom.domain.psychologist.Psychologist
import java.time.LocalDateTime

data class Consultation(
    val id: Long,
    val psychologist: Psychologist,
    val patient: Patient,
    val problemDescription: String?,
    val period: Period,
    val status: Status,
    val orderDtTm: LocalDateTime,
    val confirmationDtTm: LocalDateTime?,
) {
    enum class Status {
        PENDING,
        CONFIRMED,
        CANCELED,
        FINISHED
    }

    data class Period private constructor(
        val start: LocalDateTime,
        val end: LocalDateTime,
    ) {
        companion object {
            operator fun invoke(start: LocalDateTime, end: LocalDateTime): Either<DomainError.ValidationError, Period> =
                either {
                    ensure(start.isAfter(LocalDateTime.now())) {
                        PeriodValidationError.StartDateMustBeInFuture
                    }

                    ensure(start.isBefore(end)) {
                        PeriodValidationError.StartIsNotBeforeEnd
                    }

                    Period(
                        start = start,
                        end = end
                    )
                }
        }

        sealed class PeriodValidationError(override val message: String) : DomainError.ValidationError {
            data object StartDateMustBeInFuture : PeriodValidationError(
                "Consultation's start dttm must be in future."
            )

            data object StartIsNotBeforeEnd : PeriodValidationError(
                "Consultation's dttm must be before end dttdm."
            )
        }
    }
}
