package ru.remsely.psyhosom.security.utils

import arrow.core.getOrElse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.patient.dao.PatientFinder
import ru.remsely.psyhosom.monitoring.log.logger
import ru.remsely.psyhosom.usecase.auth.AuthCredentialsAnnotationsProvider

@Component
class AuthCredentialsAnnotationsProviderImpl(
    private val patientFinder: PatientFinder
) : AuthCredentialsAnnotationsProvider {
    private val log = logger()

    override fun getAuthUserId(): Long {
        return (SecurityContextHolder.getContext().authentication.principal as Jwt)
            .claims["id"]!!
            .toString()
            .toLong()
    }

    override fun getAuthPatientId(): Long {
        return (SecurityContextHolder.getContext().authentication.principal as Jwt)
            .claims["id"]!!
            .also { log.info("$it") }
            .toString()
            .toLong()
            .let {
                patientFinder.findPatientByAccountId(it).getOrElse {
                    throw Exception("Patient not found.")
                }.id
            }
    }
}
