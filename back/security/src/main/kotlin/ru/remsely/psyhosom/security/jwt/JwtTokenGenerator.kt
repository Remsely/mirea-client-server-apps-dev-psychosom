package ru.remsely.psyhosom.security.jwt

import org.springframework.security.core.Authentication

interface JwtTokenGenerator {
    fun generate(auth: Authentication): String
}