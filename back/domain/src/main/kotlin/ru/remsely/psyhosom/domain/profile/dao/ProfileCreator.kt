package ru.remsely.psyhosom.domain.profile.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.profile.Profile

interface ProfileCreator {
    fun createProfile(profile: Profile): Either<DomainError, Profile>
}
