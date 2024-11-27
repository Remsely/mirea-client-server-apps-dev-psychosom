package ru.remsely.psyhosom.db.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.remsely.psyhosom.db.entity.UserProfile

@Repository
interface UserProfileRepository : JpaRepository<UserProfile, Long> {
    fun findByUser_Id(userId: Long): List<UserProfile>

    fun existsByTelegramEqualsIgnoreCaseOrPhoneEqualsIgnoreCase(telegram: String, phone: String): Boolean
}
