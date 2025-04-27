package ru.remsely.psyhosom.domain.value_object

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import ru.remsely.psyhosom.domain.error.DomainError
import java.net.URI
import java.util.*

@JvmInline
value class MeetingLink private constructor(
    val value: String
) {
    companion object {
        operator fun invoke(value: String): Either<DomainError, MeetingLink> = either {
            val uri: URI = runCatching {
                URI.create(value)
            }.getOrElse {
                raise(MeetingLinkValidationError.InvalidLink(value))
            }

            ensure(uri.scheme == "https") {
                MeetingLinkValidationError.InvalidLink(value)
            }

            ensure(uri.host == "meet.jit.si") {
                MeetingLinkValidationError.InvalidLink(value)
            }

            ensure(uri.path.startsWith("/psychosom_")) {
                MeetingLinkValidationError.InvalidLink(value)
            }

            MeetingLink(value)
        }

        fun generate(): MeetingLink = MeetingLink("https://meet.jit.si/psychosom_${UUID.randomUUID()}")
    }
}

sealed class MeetingLinkValidationError(override val message: String) : DomainError.ValidationError {
    data class InvalidLink(private val link: String) : MeetingLinkValidationError(
        "Link is invalid."
    )
}