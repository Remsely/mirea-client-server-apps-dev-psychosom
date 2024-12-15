package ru.remsely.psyhosom.domain.profile.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.profile.Profile

interface ProfileFinder {
    fun findProfileByUserId(userId: Long): Either<DomainError, Profile>

    fun checkNotExistsWithUsernameInContacts(username: String): Either<DomainError, Unit>
}

sealed class UserProfileFindingError(override val message: String) : DomainError.ValidationError {
    data class NotFoundByUserId(private val userId: Long) : UserProfileFindingError(
        "Profile for user with id $userId not found."
    )

    data object ProfileWithUsernameAlreadyExists : UserProfileFindingError(
        "Profile with such username already exists."
    )
}
