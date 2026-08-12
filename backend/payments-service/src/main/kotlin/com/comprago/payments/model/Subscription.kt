package com.comprago.payments.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "subscriptions")
class Subscription(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "seller_id", nullable = false, unique = true)
    var sellerId: Long = 0,

    @Column(nullable = false, precision = 10, scale = 2)
    var monthlyFee: BigDecimal = BigDecimal("120.00"),

    @Column(nullable = false)
    var status: String = "ACTIVE",

    @Column(name = "next_billing_date")
    var nextBillingDate: LocalDateTime = LocalDateTime.now().plusMonths(1),

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
