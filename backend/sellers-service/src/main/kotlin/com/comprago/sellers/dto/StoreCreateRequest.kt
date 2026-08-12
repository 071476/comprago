package com.comprago.sellers.dto

import jakarta.validation.constraints.NotBlank

data class StoreCreateRequest(

    val sellerId: Long,

    @field:NotBlank(message = "El nombre de la tienda es obligatorio")
    val name: String,

    val description: String = "",

    @field:NotBlank(message = "La categoria es obligatoria")
    val category: String,

    val logoUrl: String = "",

    val bannerUrl: String = ""
)
