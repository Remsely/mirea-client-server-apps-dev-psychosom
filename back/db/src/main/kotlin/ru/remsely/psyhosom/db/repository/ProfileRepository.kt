package ru.remsely.psyhosom.db.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.remsely.psyhosom.db.entity.Profile

@Repository
interface ProfileRepository : JpaRepository<Profile, Long> {
    @Query("select p from Profile p where p.account.id = :accountId")
    fun findByAccountId(accountId: Long): List<Profile>

    fun existsByTelegramEqualsIgnoreCaseOrPhoneEqualsIgnoreCase(telegram: String, phone: String): Boolean

    fun deleteByAccountIdIn(accountIds: List<Long>)
}
