package com.comprago.cart.repository

import com.comprago.cart.model.CartItem
import org.springframework.data.jpa.repository.JpaRepository

interface CartItemRepository : JpaRepository<CartItem, Long> {
    fun findByCartIdAndProductId(cartId: Long, productId: Long): CartItem?
    fun deleteByCartId(cartId: Long)
}
