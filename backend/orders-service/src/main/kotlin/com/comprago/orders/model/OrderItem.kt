package com.comprago.orders.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "order_items")
class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    var order: Order = Order(),

    @Column(name = "product_id", nullable = false)
    var productId: Long = 0,

    @Column(name = "product_name", nullable = false)
    var productName: String = "",

    @Column(nullable = false, precision = 10, scale = 2)
    var price: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    var quantity: Int = 1,

    @Column(name = "image_url")
    var imageUrl: String = ""
) {
    val subtotal: BigDecimal
        get() = price.multiply(BigDecimal(quantity))
}
