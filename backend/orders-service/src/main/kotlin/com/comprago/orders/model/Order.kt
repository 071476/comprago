package com.comprago.orders.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "buyer_id", nullable = false)
    var buyerId: Long = 0,

    @Column(name = "seller_id", nullable = false)
    var sellerId: Long = 0,

    @Column(name = "store_id", nullable = false)
    var storeId: Long = 0,

    @Column(nullable = false, precision = 10, scale = 2)
    var total: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    var status: String = "PENDING",

    @Column(name = "shipping_address", columnDefinition = "TEXT")
    var shippingAddress: String = "",

    @Column(name = "payment_method")
    var paymentMethod: String = "",

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    var items: MutableList<OrderItem> = mutableListOf()
)
