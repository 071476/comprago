package com.comprago.products.dto

import java.math.BigDecimal

data class CreateProductRequest(
    val name: String,
    val description: String,
    val price: BigDecimal,
    val category: String,
    val storeId: Long,
    val imageUrls: List<String> = emptyList()
)

data class ProductResponse(
    val id: Long,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val category: String,
    val imageUrls: List<String>,
    val sellerId: Long,
    val storeId: Long,
    val active: Boolean,
    val message: String
)
