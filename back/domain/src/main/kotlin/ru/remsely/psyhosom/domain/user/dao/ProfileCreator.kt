package ru.remsely.psyhosom.domain.user.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.errors.DomainError
import ru.remsely.psyhosom.domain.user.User

interface ProfileCreator {
    fun createProfile(profile: User.Profile): Either<DomainError, User.Profile>
}
