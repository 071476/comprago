package com.comprago.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class LoginRequest(

    @field:Email(message = "Email inválido")
    @field:NotBlank
    val email: String,

    @field:NotBlank(message = "La contraseña es obligatoria")
    val password: String
)
