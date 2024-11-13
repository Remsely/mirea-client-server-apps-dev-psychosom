package ru.remsely.psyhosom.domain.auth

interface AuthCredentialsAnnotationsProvider {
    fun getAuthUserId(): Long
}
