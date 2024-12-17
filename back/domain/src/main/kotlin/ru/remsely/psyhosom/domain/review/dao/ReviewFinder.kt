package ru.remsely.psyhosom.domain.review.dao

import ru.remsely.psyhosom.domain.review.Review

interface ReviewFinder {
    fun existReviewByPatientIdAndPsychologistId(patientId: Long, psychologistId: Long): Boolean

    fun findReviewsByPsychologistId(psychologistId: Long): List<Review>
}
