package com.comprago.products.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "products")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var name: String = "",

    @Column(columnDefinition = "TEXT")
    var description: String = "",

    @Column(nullable = false, precision = 10, scale = 2)
    var price: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    var category: String = "",

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = [JoinColumn(name = "product_id")])
    @Column(name = "image_url")
    var imageUrls: MutableList<String> = mutableListOf(),

    @Column(name = "seller_id", nullable = false)
    var sellerId: Long = 0,

    @Column(name = "store_id", nullable = false)
    var storeId: Long = 0,

    @Column(nullable = false)
    var active: Boolean = true,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
)
