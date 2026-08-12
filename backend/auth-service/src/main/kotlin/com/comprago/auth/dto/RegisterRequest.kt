package com.comprago.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequest(

    @field:NotBlank(message = "El nombre es obligatorio")
    val firstName: String,

    @field:NotBlank(message = "El apellido es obligatorio")
    val lastName: String,

    @field:Email(message = "Email inválido")
    @field:NotBlank(message = "El email es obligatorio")
    val email: String,

    @field:Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @field:NotBlank(message = "La contraseña es obligatoria")
    val password: String,

    val role: String = "BUYER"
)
