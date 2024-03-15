package com.example.mojivet.authentication.data

data class TokenResponse(
    val message: String? = null,
    val token: String? = null,
    val email: String? = null,
    val userId: Int? = null,
)
