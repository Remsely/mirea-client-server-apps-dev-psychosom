package ru.remsely.psyhosom.db.entity

import jakarta.persistence.*

@Entity
@Table(name = "user_profile")
data class UserProfile (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @JoinColumn(nullable = false, unique = true)
    @OneToOne
    val user: User,

    @Column(name = "phone_number", nullable = true)
    val phone: String?,

    @Column(name = "telegram_username", nullable = true)
    val telegram: String?,

    @Column(name = "first_name", nullable = true)
    val firstName: String?,

    @Column(name = "last_name", nullable = true)
    val lastName: String?
)
