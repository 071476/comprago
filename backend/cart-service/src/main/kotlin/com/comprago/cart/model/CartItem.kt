package com.comprago.cart.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "cart_items")
class CartItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    var cart: Cart = Cart(),

    @Column(name = "product_id", nullable = false)
    var productId: Long = 0,

    @Column(name = "product_name", nullable = false)
    var productName: String = "",

    @Column(nullable = false, precision = 10, scale = 2)
    var price: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    var quantity: Int = 1,

    @Column(name = "image_url")
    var imageUrl: String = "",

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    val subtotal: BigDecimal
        get() = price.multiply(BigDecimal(quantity))
}
