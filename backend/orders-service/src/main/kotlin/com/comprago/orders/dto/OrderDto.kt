package com.comprago.orders.dto

import java.math.BigDecimal

data class CreateOrderRequest(
    val buyerId: Long,
    val sellerId: Long,
    val storeId: Long,
    val productId: Long,
    val productName: String,
    val quantity: Int,
    val unitPrice: BigDecimal,
    val shippingAddress: String
)

data class UpdateOrderStatusRequest(
    val status: String
)

data class OrderResponse(
    val id: Long,
    val buyerId: Long,
    val sellerId: Long,
    val storeId: Long,
    val productId: Long,
    val productName: String,
    val quantity: Int,
    val unitPrice: BigDecimal,
    val totalAmount: BigDecimal,
    val status: String,
    val shippingAddress: String,
    val createdAt: String,
    val updatedAt: String
)
