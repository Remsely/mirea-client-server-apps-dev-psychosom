package ru.remsely.psyhosom.domain.user.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.errors.DomainError
import ru.remsely.psyhosom.domain.user.User

interface UserFinder {
    fun findUserByUsername(username: String): Either<DomainError, User>

    fun findUserById(id: Long): Either<DomainError, User>
}

sealed class UserFindingError(override val message: String) : DomainError.ValidationError {
    data class NotFoundByUsername(private val username: String) : UserFindingError(
        "User with username $username not found."
    )

    data class NotFoundById(private val id: Long) : UserFindingError(
        "User with id $id not found."
    )
}
