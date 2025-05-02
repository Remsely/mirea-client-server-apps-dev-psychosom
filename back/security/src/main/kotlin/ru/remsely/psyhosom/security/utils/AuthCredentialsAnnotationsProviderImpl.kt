package ru.remsely.psyhosom.security.utils

import arrow.core.getOrElse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.patient.dao.PatientFinder
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistFinder
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.auth.AuthCredentialsAnnotationsProvider

@Component
class AuthCredentialsAnnotationsProviderImpl(
    private val patientFinder: PatientFinder,
    private val psychologistFinder: PsychologistFinder,
) : AuthCredentialsAnnotationsProvider {
    private val log = logger()

    override fun getAuthUserId(): Long {
        return (SecurityContextHolder.getContext().authentication.principal as Jwt)
            .claims["id"]!!
            .toString()
            .toLong()
            .also {
                log.info("Successfully get account id: $it from JWT token.")
            }
    }

    override fun getAuthPatientId(): Long {
        return (SecurityContextHolder.getContext().authentication.principal as Jwt)
            .claims["id"]!!
            .toString()
            .toLong()
            .let {
                patientFinder.findPatientByAccountId(it).getOrElse {
                    throw Exception("Patient not found.")
                }.id
            }
            .also {
                log.info("Successfully get patient id: $it from JWT token.")
            }
    }

    override fun getPsychologistId(): Long {
        return (SecurityContextHolder.getContext().authentication.principal as Jwt)
            .claims["id"]!!
            .toString()
            .toLong()
            .let {
                psychologistFinder.findPsychologistByAccountId(it).getOrElse {
                    throw Exception("Psychologist not found.")
                }.id
            }
            .also {
                log.info("Successfully get psychologist id: $it from JWT token.")
            }
    }
}
