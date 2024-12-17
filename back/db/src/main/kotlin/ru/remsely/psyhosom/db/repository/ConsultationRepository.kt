package ru.remsely.psyhosom.db.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import ru.remsely.psyhosom.db.entity.Consultation
import ru.remsely.psyhosom.domain.consultation.Consultation as DomainConsultation

interface ConsultationRepository : JpaRepository<Consultation, Long> {
    fun existsSessionByPatientIdAndPsychologistIdAndStatusNotIn(
        patientId: Long,
        psychologistId: Long,
        statuses: List<DomainConsultation.Status>
    ): Boolean

    @EntityGraph(attributePaths = ["patient", "psychologist"])
    fun findByPatientIdAndPsychologistIdAndStatusNotIn(
        patientId: Long,
        psychologistId: Long,
        statuses: List<DomainConsultation.Status>
    ): List<Consultation>
}
