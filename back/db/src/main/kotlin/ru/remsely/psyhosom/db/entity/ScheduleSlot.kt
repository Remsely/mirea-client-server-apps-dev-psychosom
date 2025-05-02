package ru.remsely.psyhosom.db.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalTime

@Entity
@Table(name = "schedule_slot")
data class ScheduleSlot(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "psychologist_id", nullable = false)
    val psychologist: Psychologist,

    @Column(nullable = false)
    val date: LocalDate,

    @Column(name = "start_tm", nullable = false)
    val startTm: LocalTime,

    @Column(name = "end_tm", nullable = false)
    val endTm: LocalTime,

    @Column(nullable = false)
    val available: Boolean
)
