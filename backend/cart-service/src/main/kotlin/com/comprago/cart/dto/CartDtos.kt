package com.comprago.cart.dto

import java.math.BigDecimal

data class AddToCartRequest(
    val productId: Long,
    val productName: String,
    val price: BigDecimal,
    val quantity: Int = 1,
    val imageUrl: String = ""
)

data class UpdateQuantityRequest(
    val quantity: Int
)

data class CartItemResponse(
    val id: Long,
    val productId: Long,
    val productName: String,
    val price: BigDecimal,
    val quantity: Int,
    val subtotal: BigDecimal,
    val imageUrl: String
)

data class CartResponse(
    val id: Long,
    val buyerId: Long,
    val items: List<CartItemResponse>,
    val total: BigDecimal,
    val itemCount: Int,
    val message: String
)
