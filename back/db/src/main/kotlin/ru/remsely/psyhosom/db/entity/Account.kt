package ru.remsely.psyhosom.db.entity

import jakarta.persistence.*
import ru.remsely.psyhosom.domain.account.Account

@Entity
@Table(name = "account")
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(nullable = false, unique = true, length = 255)
    val username: String,

    @Column(nullable = false, length = 255)
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Account.Role,
)
