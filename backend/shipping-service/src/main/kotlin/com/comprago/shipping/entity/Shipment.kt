package com.comprago.shipping.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "shipments")
data class Shipment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val orderId: Long = 0,
    val sellerId: Long = 0,
    val buyerId: Long = 0,
    val trackingNumber: String = "",
    val carrier: String = "",
    val status: String = "PENDING",
    val originAddress: String = "",
    val destinationAddress: String = "",
    val estimatedDelivery: String = "",

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
