package ru.remsely.psyhosom.db.entity

import jakarta.persistence.*
import ru.remsely.psyhosom.domain.account.Account
import java.time.LocalDateTime

@Entity
@Table(name = "account")
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false, unique = true, length = 255)
    val username: String,

    @Column(nullable = false, length = 255)
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Account.Role,

    @Column(name = "is_confirmed", nullable = false)
    val isConfirmed: Boolean,

    @Column(name = "tg_bot_token", nullable = false, unique = true)
    val tgBotToken: String,

    @Column(name = "tg_chat_id", nullable = true)
    val tgChatId: Long?,

    @Column(name = "registration_dttm", nullable = false)
    val registrationDate: LocalDateTime
)
