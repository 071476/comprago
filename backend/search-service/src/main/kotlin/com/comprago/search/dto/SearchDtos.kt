package com.comprago.search.dto

import java.math.BigDecimal

data class IndexProductRequest(
    val productId: Long,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val category: String,
    val sellerId: Long,
    val storeId: Long,
    val storeName: String = "",
    val imageUrl: String = "",
    val inStock: Boolean = true
)

data class SearchResultResponse(
    val id: Long,
    val productId: Long,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val category: String,
    val storeName: String,
    val imageUrl: String,
    val inStock: Boolean
)
