package com.comprago.cart.repository

import com.comprago.cart.model.Cart
import org.springframework.data.jpa.repository.JpaRepository

interface CartRepository : JpaRepository<Cart, Long> {
    fun findByBuyerId(buyerId: Long): Cart?
}
