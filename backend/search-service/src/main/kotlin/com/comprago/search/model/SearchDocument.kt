package com.comprago.search.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "search_index")
class SearchDocument(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "product_id", nullable = false, unique = true)
    var productId: Long = 0,

    @Column(nullable = false)
    var name: String = "",

    @Column(columnDefinition = "TEXT")
    var description: String = "",

    @Column(nullable = false, precision = 10, scale = 2)
    var price: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    var category: String = "",

    @Column(name = "seller_id", nullable = false)
    var sellerId: Long = 0,

    @Column(name = "store_id", nullable = false)
    var storeId: Long = 0,

    @Column(name = "store_name")
    var storeName: String = "",

    @Column(name = "image_url")
    var imageUrl: String = "",

    @Column(name = "in_stock", nullable = false)
    var inStock: Boolean = true
)
