package com.example.mojivet.authentication.data

data class RegistrationRequest(
    val name: String,
    val email: String,
    val password: String,
    val password_confirmation: String
)
