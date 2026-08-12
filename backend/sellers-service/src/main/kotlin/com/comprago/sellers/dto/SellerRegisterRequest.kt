package com.comprago.sellers.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SellerRegisterRequest(

    val userId: Long,

    @field:NotBlank(message = "El nombre es obligatorio")
    val firstName: String,

    @field:NotBlank(message = "El apellido es obligatorio")
    val lastName: String,

    @field:Email(message = "Email invalido")
    @field:NotBlank(message = "El email es obligatorio")
    val email: String,

    @field:NotBlank(message = "El telefono es obligatorio")
    val phone: String
)
