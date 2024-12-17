package ru.remsely.psyhosom.domain.consultation.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.consultation.Consultation

interface ConsultationCreator {
    fun createConsultation(consultation: Consultation): Either<DomainError, Consultation>
}
