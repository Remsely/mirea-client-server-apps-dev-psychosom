package ru.remsely.psyhosom.db.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.remsely.psyhosom.db.entity.Account

@Repository
interface AccountRepository : JpaRepository<Account, Long> {
    fun findByUsername(username: String): Account?

    fun existsByUsername(username: String): Boolean
}
