package com.comprago.inventory.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "inventory")
class InventoryItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "product_id", nullable = false, unique = true)
    var productId: Long = 0,

    @Column(nullable = false)
    var stock: Int = 0,

    @Column(name = "reserved", nullable = false)
    var reserved: Int = 0,

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    val available: Int get() = stock - reserved
}
