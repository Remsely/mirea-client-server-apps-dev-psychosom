package ru.remsely.psihosom.security.jwt

import org.springframework.security.core.Authentication

interface JwtTokenGenerator {
    fun generate(auth: Authentication): String
}