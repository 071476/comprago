package com.comprago.payments.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "payments")
class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "order_id", nullable = false)
    var orderId: Long = 0,

    @Column(name = "buyer_id", nullable = false)
    var buyerId: Long = 0,

    @Column(name = "seller_id", nullable = false)
    var sellerId: Long = 0,

    @Column(name = "order_total", nullable = false, precision = 10, scale = 2)
    var orderTotal: BigDecimal = BigDecimal.ZERO,

    @Column(name = "commission_amount", nullable = false, precision = 10, scale = 2)
    var commissionAmount: BigDecimal = BigDecimal.ZERO,

    @Column(name = "commission_rate", nullable = false, precision = 5, scale = 2)
    var commissionRate: BigDecimal = BigDecimal("3.50"),

    @Column(name = "seller_receives", nullable = false, precision = 10, scale = 2)
    var sellerReceives: BigDecimal = BigDecimal.ZERO,

    @Column(name = "payment_method", nullable = false)
    var paymentMethod: String = "",

    @Column(nullable = false)
    var status: String = "PENDING",

    @Column(name = "transaction_id")
    var transactionId: String = "",

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
