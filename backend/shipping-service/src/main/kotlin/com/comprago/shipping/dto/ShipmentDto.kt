package com.comprago.shipping.dto

data class CreateShipmentRequest(
    val orderId: Long,
    val sellerId: Long,
    val buyerId: Long,
    val carrier: String,
    val originAddress: String,
    val destinationAddress: String,
    val estimatedDelivery: String
)

data class UpdateShipmentStatusRequest(
    val status: String
)

data class ShipmentResponse(
    val id: Long,
    val orderId: Long,
    val sellerId: Long,
    val buyerId: Long,
    val trackingNumber: String,
    val carrier: String,
    val status: String,
    val originAddress: String,
    val destinationAddress: String,
    val estimatedDelivery: String,
    val createdAt: String,
    val updatedAt: String
)
