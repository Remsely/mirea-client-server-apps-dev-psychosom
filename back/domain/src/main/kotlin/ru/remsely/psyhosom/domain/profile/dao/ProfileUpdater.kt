package ru.remsely.psyhosom.domain.profile.dao

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.profile.Profile

interface ProfileUpdater {
    fun updateProfile(profile: Profile): Either<DomainError, Profile>
}
