package ru.remsely.psyhosom.usecase.user

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.profile.Profile
import ru.remsely.psyhosom.domain.profile.event.UpdateProfileEvent

interface ProfileManager {
    fun createOrUpdateProfile(event: UpdateProfileEvent): Either<DomainError, Profile>

    fun findProfileByUserId(userId: Long): Either<DomainError, Profile>
}

sealed class UserProfileManagingError(override val message: String) : DomainError.BusinessLogicError {
    data class UserProfileNotFound(private val userId: Long) : UserProfileManagingError(
        "Profile for user with id $userId not found."
    )

    data object ProfileUsernameMustBeInContacts : UserProfileManagingError(
        "User username must be present in contacts and cannot be changed."
    )
}
