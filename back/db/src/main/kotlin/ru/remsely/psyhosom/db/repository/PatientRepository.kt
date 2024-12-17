package ru.remsely.psyhosom.db.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.remsely.psyhosom.db.entity.Patient

@Repository
interface PatientRepository : JpaRepository<Patient, Long> {
    @Query("select p from Patient p where p.account.id = :accountId")
    fun findByAccountId(accountId: Long): List<Patient>

    fun existsByTelegramEqualsIgnoreCaseOrPhoneEqualsIgnoreCase(telegram: String, phone: String): Boolean

    fun deleteByAccountIdIn(accountIds: List<Long>)
}
