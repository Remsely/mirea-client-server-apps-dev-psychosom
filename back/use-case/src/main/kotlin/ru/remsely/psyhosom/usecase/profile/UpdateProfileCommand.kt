package ru.remsely.psyhosom.usecase.profile

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.profile.Profile
import ru.remsely.psyhosom.domain.profile.event.UpdateProfileEvent

interface UpdateProfileCommand {
    fun execute(event: UpdateProfileEvent): Either<DomainError, Profile>
}

sealed class ProfileUpdateError(override val message: String) : DomainError.BusinessLogicError {
    data class ProfileNotFound(private val accountId: Long) : ProfileUpdateError(
        "Profile for account with id $accountId not found."
    )

    data object ProfileUsernameMustBeInContacts : ProfileUpdateError(
        "User username must be present in contacts and cannot be changed."
    )
}
