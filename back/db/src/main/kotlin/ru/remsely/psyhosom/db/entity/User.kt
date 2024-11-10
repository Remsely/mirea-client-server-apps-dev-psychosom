package ru.remsely.psyhosom.db.entity

import jakarta.persistence.*
import ru.remsely.psyhosom.domain.user.User

@Entity
@Table(name = "app_user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false, unique = true, length = 255)
    val username: String,

    @Column(nullable = false, length = 255)
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: User.Role,
)
