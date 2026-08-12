package com.comprago.customers.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "addresses")
class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    var customer: Customer = Customer(),

    @Column(name = "label", nullable = false)
    var label: String = "",

    @Column(nullable = false)
    var street: String = "",

    @Column(nullable = false)
    var city: String = "",

    @Column(nullable = false)
    var state: String = "",

    @Column(name = "zip_code", nullable = false)
    var zipCode: String = "",

    @Column(nullable = false)
    var country: String = "México",

    @Column(name = "is_default")
    var isDefault: Boolean = false,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
)
