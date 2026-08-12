package com.comprago.shipping.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "shipments")
class Shipment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "order_id", nullable = false, unique = true)
    var orderId: Long = 0,

    @Column(name = "buyer_id", nullable = false)
    var buyerId: Long = 0,

    @Column(name = "seller_id", nullable = false)
    var sellerId: Long = 0,

    @Column(name = "tracking_number", nullable = false, unique = true)
    var trackingNumber: String = "",

    @Column(name = "carrier", nullable = false)
    var carrier: String = "",

    @Column(nullable = false)
    var status: String = "PENDING",

    @Column(name = "shipping_address", columnDefinition = "TEXT", nullable = false)
    var shippingAddress: String = "",

    @Column(name = "shipping_cost", nullable = false, precision = 10, scale = 2)
    var shippingCost: BigDecimal = BigDecimal.ZERO,

    @Column(name = "estimated_delivery")
    var estimatedDelivery: LocalDateTime? = null,

    @Column(name = "delivered_at")
    var deliveredAt: LocalDateTime? = null,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "shipment", cascade = [CascadeType.ALL], orphanRemoval = true)
    var events: MutableList<ShipmentEvent> = mutableListOf()
)
