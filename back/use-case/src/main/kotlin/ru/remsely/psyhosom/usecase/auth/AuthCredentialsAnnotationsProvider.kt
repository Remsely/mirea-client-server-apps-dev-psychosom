package ru.remsely.psyhosom.usecase.auth

interface AuthCredentialsAnnotationsProvider {
    fun getAuthUserId(): Long

    fun getAuthPatientId(): Long

    fun getPsychologistId(): Long
}
