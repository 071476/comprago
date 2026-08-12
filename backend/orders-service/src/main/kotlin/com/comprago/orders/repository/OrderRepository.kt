package com.comprago.orders.repository

import com.comprago.orders.model.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {
    fun findByBuyerId(buyerId: Long): List<Order>
    fun findBySellerId(sellerId: Long): List<Order>
    fun findByStatus(status: String): List<Order>
}
