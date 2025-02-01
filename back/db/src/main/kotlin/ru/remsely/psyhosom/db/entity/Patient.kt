package ru.remsely.psyhosom.db.entity

import jakarta.persistence.*

@Entity
@Table(name = "patient_profile")
data class Patient(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @JoinColumn(nullable = false, unique = true)
    @OneToOne(fetch = FetchType.EAGER)
    val account: Account,

    @Column(name = "first_name", nullable = false)
    val firstName: String,

    @Column(name = "last_name", nullable = false)
    val lastName: String
)
