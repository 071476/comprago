package com.comprago.sellers.dto

data class StoreResponse(
    val id: Long,
    val name: String,
    val description: String,
    val category: String,
    val logoUrl: String,
    val bannerUrl: String,
    val active: Boolean,
    val message: String
)
