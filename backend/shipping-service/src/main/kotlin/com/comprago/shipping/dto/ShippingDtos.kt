package com.comprago.shipping.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class CreateShipmentRequest(
    val orderId: Long,
    val buyerId: Long,
    val sellerId: Long,
    val shippingAddress: String,
    val carrier: String = "Estafeta",
    val shippingCost: BigDecimal = BigDecimal("99.00")
)

data class UpdateStatusRequest(
    val status: String,
    val location: String = "",
    val description: String = ""
)

data class ShipmentEventResponse(
    val id: Long,
    val status: String,
    val description: String,
    val location: String,
    val eventTime: LocalDateTime
)

data class ShipmentResponse(
    val id: Long,
    val orderId: Long,
    val buyerId: Long,
    val sellerId: Long,
    val trackingNumber: String,
    val carrier: String,
    val status: String,
    val shippingAddress: String,
    val shippingCost: BigDecimal,
    val estimatedDelivery: LocalDateTime?,
    val deliveredAt: LocalDateTime?,
    val events: List<ShipmentEventResponse>,
    val message: String
)
