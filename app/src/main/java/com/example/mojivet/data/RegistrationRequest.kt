package com.example.mojivet.data

data class RegistrationRequest(
    val name: String,
    val email: String,
    val password: String,
    val confirm_password: String
)
