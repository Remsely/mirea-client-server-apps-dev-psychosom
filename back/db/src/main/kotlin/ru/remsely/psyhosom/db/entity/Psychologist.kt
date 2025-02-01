package ru.remsely.psyhosom.db.entity

import jakarta.persistence.*

@Entity
@Table(name = "psychologist_profile")
data class Psychologist(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @JoinColumn(nullable = false, unique = true)
    @OneToOne
    val account: Account,

    @Column(name = "first_name", nullable = false)
    val firstName: String,

    @Column(name = "last_name", nullable = false)
    val lastName: String
)
