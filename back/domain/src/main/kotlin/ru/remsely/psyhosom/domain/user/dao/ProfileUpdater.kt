package ru.remsely.psyhosom.domain.user.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.errors.DomainError
import ru.remsely.psyhosom.domain.user.User

interface ProfileUpdater {
    fun updateProfile(profile: User.Profile): Either<DomainError, User.Profile>
}
