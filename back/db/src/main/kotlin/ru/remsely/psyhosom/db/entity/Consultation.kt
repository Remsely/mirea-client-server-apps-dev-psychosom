package ru.remsely.psyhosom.db.entity

import jakarta.persistence.*
import ru.remsely.psyhosom.domain.consultation.Consultation
import java.time.LocalDateTime

@Entity
@Table(name = "consultation")
data class Consultation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val psychologist: Psychologist,

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val patient: Patient,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val status: Consultation.Status,

    @Column(name = "problem_description", nullable = true)
    val problemDescription: String?,

    @Column(name = "start_dttm", nullable = false)
    val startDtTm: LocalDateTime,

    @Column(name = "end_dttm", nullable = false)
    val endDtTm: LocalDateTime,

    @Column(name = "order_dttm", nullable = false)
    val orderDtTm: LocalDateTime,

    @Column(name = "confirmation_dttm", nullable = true)
    val confirmationDtTm: LocalDateTime?,
)
