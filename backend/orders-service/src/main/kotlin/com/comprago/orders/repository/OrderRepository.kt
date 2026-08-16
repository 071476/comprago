package com.comprago.orders.repository

import com.comprago.orders.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
    fun findBySellerId(sellerId: Long): List<Order>
    fun findByBuyerId(buyerId: Long): List<Order>
    fun findByStatus(status: String): List<Order>
}
