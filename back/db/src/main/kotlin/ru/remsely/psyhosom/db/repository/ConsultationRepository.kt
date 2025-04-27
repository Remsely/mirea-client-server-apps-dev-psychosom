package ru.remsely.psyhosom.db.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import ru.remsely.psyhosom.db.entity.Consultation
import java.time.LocalDateTime
import ru.remsely.psyhosom.domain.consultation.Consultation as DomainConsultation

interface ConsultationRepository : JpaRepository<Consultation, Long> {
    fun existsByPatientIdAndPsychologistIdAndStatusNotIn(
        patientId: Long,
        psychologistId: Long,
        statuses: List<DomainConsultation.Status>
    ): Boolean

    fun existsByPatientIdAndPsychologistIdAndStatus(
        patientId: Long,
        psychologistId: Long,
        status: DomainConsultation.Status
    ): Boolean

    @EntityGraph(attributePaths = ["patient", "psychologist"])
    fun findByPatientIdAndPsychologistIdAndStatusNotIn(
        patientId: Long,
        psychologistId: Long,
        statuses: List<DomainConsultation.Status>
    ): List<Consultation>

    @EntityGraph(attributePaths = ["patient", "psychologist"])
    fun findAllByStatusAndStartDtTmIsBefore(
        status: DomainConsultation.Status,
        startDtTm: LocalDateTime
    ): List<Consultation>

    @EntityGraph(attributePaths = ["patient", "psychologist"])
    fun findAllByStatusAndEndDtTmBefore(
        status: DomainConsultation.Status,
        startDtTm: LocalDateTime
    ): List<Consultation>
}
