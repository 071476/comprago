package com.comprago.inventory.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "inventory")
data class InventoryItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val productId: Long = 0,
    val sellerId: Long = 0,
    val productName: String = "",
    val stock: Int = 0,
    val minStock: Int = 5,
    val maxStock: Int = 1000,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
