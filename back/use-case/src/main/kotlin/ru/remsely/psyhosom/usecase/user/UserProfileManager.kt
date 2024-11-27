package ru.remsely.psyhosom.usecase.user

import arrow.core.Either
import ru.remsely.psyhosom.domain.errors.DomainError
import ru.remsely.psyhosom.domain.user.User
import ru.remsely.psyhosom.domain.user.event.UpdateUserProfileEvent

interface UserProfileManager {
    fun createOrUpdateProfile(event: UpdateUserProfileEvent): Either<DomainError, User.Profile>

    fun findProfileByUserId(userId: Long): Either<DomainError, User.Profile>
}

sealed class UserProfileManagingError(override val message: String) : DomainError.BusinessLogicError {
    data class UserProfileNotFound(private val userId: Long) : UserProfileManagingError(
        "Profile for user with id $userId not found."
    )

    data object ProfileUsernameMustBeInContacts : UserProfileManagingError(
        "User username must be present in contacts and cannot be changed."
    )
}
