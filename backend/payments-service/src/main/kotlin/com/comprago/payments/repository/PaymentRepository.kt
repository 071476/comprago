package com.comprago.payments.repository

import com.comprago.payments.model.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PaymentRepository : JpaRepository<Payment, Long> {
    fun findByOrderId(orderId: Long): Payment?
    fun findBySellerId(sellerId: Long): List<Payment>
    fun findByBuyerId(buyerId: Long): List<Payment>
    fun findByStatus(status: String): List<Payment>

    @Query("SELECT COALESCE(SUM(p.orderTotal), 0) FROM Payment p WHERE p.sellerId = :sellerId AND p.status = 'COMPLETED'")
    fun sumTotalSalesBySeller(sellerId: Long): java.math.BigDecimal

    @Query("SELECT COALESCE(SUM(p.commissionAmount), 0) FROM Payment p WHERE p.sellerId = :sellerId AND p.status = 'COMPLETED'")
    fun sumCommissionsBySeller(sellerId: Long): java.math.BigDecimal

    @Query("SELECT COALESCE(SUM(p.sellerReceives), 0) FROM Payment p WHERE p.sellerId = :sellerId AND p.status = 'COMPLETED'")
    fun sumSellerEarnings(sellerId: Long): java.math.BigDecimal
}
