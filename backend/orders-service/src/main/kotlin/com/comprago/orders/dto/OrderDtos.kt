package com.comprago.orders.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class CreateOrderRequest(
    val sellerId: Long,
    val storeId: Long,
    val shippingAddress: String,
    val paymentMethod: String,
    val items: List<OrderItemRequest>
)

data class OrderItemRequest(
    val productId: Long,
    val productName: String,
    val price: BigDecimal,
    val quantity: Int,
    val imageUrl: String = ""
)

data class OrderItemResponse(
    val id: Long,
    val productId: Long,
    val productName: String,
    val price: BigDecimal,
    val quantity: Int,
    val subtotal: BigDecimal,
    val imageUrl: String
)

data class OrderResponse(
    val id: Long,
    val buyerId: Long,
    val sellerId: Long,
    val storeId: Long,
    val items: List<OrderItemResponse>,
    val total: BigDecimal,
    val status: String,
    val shippingAddress: String,
    val paymentMethod: String,
    val createdAt: LocalDateTime,
    val message: String
)

data class UpdateStatusRequest(
    val status: String
)
