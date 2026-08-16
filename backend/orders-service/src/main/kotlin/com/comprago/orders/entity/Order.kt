package com.comprago.orders.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val buyerId: Long = 0,
    val sellerId: Long = 0,
    val storeId: Long = 0,
    val productId: Long = 0,
    val productName: String = "",
    val quantity: Int = 1,
    val unitPrice: BigDecimal = BigDecimal.ZERO,
    val totalAmount: BigDecimal = BigDecimal.ZERO,
    val status: String = "PENDING",
    val shippingAddress: String = "",

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
