package com.comprago.sellers.dto

data class SellerResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val status: String,
    val message: String
)
