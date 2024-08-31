package ru.remsely.psihosom.db.repository

import ru.remsely.psihosom.db.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?

    fun existsByUsername(username: String): Boolean
}
