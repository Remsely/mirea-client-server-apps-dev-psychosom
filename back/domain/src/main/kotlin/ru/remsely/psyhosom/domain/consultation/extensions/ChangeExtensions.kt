package ru.remsely.psyhosom.domain.consultation.extensions

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import ru.remsely.psyhosom.domain.consultation.Consultation
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.value_object.MeetingLink

fun Consultation.notifyConsultation(meetingLink: MeetingLink): Either<DomainError, Consultation> = either {
    ensure(status == Consultation.Status.CONFIRMED) {
        ConsultationChangeValidationError.InvalidStatusToNotify(status)
    }
    copy(
        status = Consultation.Status.NOTIFIED,
        meetingLink = meetingLink
    )
}

fun Consultation.finishConsultation(): Either<DomainError, Consultation> = either {
    ensure(status == Consultation.Status.NOTIFIED) {
        ConsultationChangeValidationError.InvalidStatusToFinish(status)
    }
    copy(
        status = Consultation.Status.FINISHED
    )
}

sealed class ConsultationChangeValidationError(override val message: String) : DomainError.ValidationError {
    data class InvalidStatusToNotify(
        private val status: Consultation.Status
    ) : ConsultationChangeValidationError(
        "Consultation with status $status cannot be notified."
    )

    data class InvalidStatusToFinish(
        private val status: Consultation.Status
    ) : ConsultationChangeValidationError(
        "Consultation with status $status cannot be finished."
    )
}

