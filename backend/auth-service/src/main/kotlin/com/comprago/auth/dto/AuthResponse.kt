package com.comprago.auth.dto

data class AuthResponse(
    val token: String,
    val email: String,
    val role: String,
    val message: String
)
