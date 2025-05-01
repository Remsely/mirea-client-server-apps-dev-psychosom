package ru.remsely.psyhosom.db.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "review")
data class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @JoinColumn(name = "patient_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val patient: Patient,

    @JoinColumn(name = "psychologist_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val psychologist: Psychologist,

    val rating: Int,

    val text: String,

    val date: LocalDate
)
