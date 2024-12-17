package ru.remsely.psyhosom.db.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.remsely.psyhosom.db.entity.Review

@Repository
interface ReviewRepository : JpaRepository<Review, Long> {
    fun existsByPatientIdAndPsychologistId(patientId: Long, psychologistId: Long): Boolean

    fun findByPsychologistId(psychologistId: Long): List<Review>
}
