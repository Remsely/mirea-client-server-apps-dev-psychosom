package ru.remsely.psyhosom.db.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.remsely.psyhosom.db.entity.Consultation
import java.time.LocalDate
import java.time.LocalTime
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

    @EntityGraph(attributePaths = ["patient", "psychologist", "scheduleSlot"])
    fun findByPatientIdAndPsychologistIdAndStatusNotIn(
        patientId: Long,
        psychologistId: Long,
        statuses: List<DomainConsultation.Status>
    ): List<Consultation>

    @EntityGraph(attributePaths = ["patient", "psychologist", "scheduleSlot"])
    @Query(
        """
        select c 
        from Consultation c
        where c.status = :status
           and (c.scheduleSlot.date < :date or (c.scheduleSlot.date = :date and c.scheduleSlot.startTm < :time))
        """
    )
    fun findAllByStatusAndStartDtTmIsBefore(
        status: DomainConsultation.Status,
        date: LocalDate,
        time: LocalTime
    ): List<Consultation>

    @EntityGraph(attributePaths = ["patient", "psychologist", "scheduleSlot"])
    @Query(
        """
        select c 
        from Consultation c
        where c.status = :status
           and (c.scheduleSlot.date < :date or (c.scheduleSlot.date = :date and c.scheduleSlot.startTm < :time))
        """
    )
    fun findAllByStatusAndEndDtTmBefore(
        status: DomainConsultation.Status,
        date: LocalDate,
        time: LocalTime
    ): List<Consultation>
}
