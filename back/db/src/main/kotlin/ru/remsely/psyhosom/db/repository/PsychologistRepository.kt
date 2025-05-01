package ru.remsely.psyhosom.db.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.remsely.psyhosom.db.entity.Psychologist

@Repository
interface PsychologistRepository : JpaRepository<Psychologist, Long> {
    fun findByAccountId(accountId: Long): Psychologist?
}
