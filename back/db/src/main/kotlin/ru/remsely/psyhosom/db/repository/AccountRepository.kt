package ru.remsely.psyhosom.db.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.remsely.psyhosom.db.entity.Account
import java.time.LocalDateTime

@Repository
interface AccountRepository : JpaRepository<Account, Long> {
    fun findByUsername(username: String): Account?

    fun existsByUsername(username: String): Boolean

    fun findByTgBotToken(tgBotToken: String): Account?

    @Query("select a from Account a where a.isConfirmed = false and a.registrationDate < :outdatedDate")
    fun findOutdatedAccounts(outdatedDate: LocalDateTime): List<Account>
}
