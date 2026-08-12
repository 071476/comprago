package com.comprago.payments.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class ProcessPaymentRequest(
    val orderId: Long,
    val buyerId: Long,
    val sellerId: Long,
    val orderTotal: BigDecimal,
    val paymentMethod: String
)

data class PaymentResponse(
    val id: Long,
    val orderId: Long,
    val buyerId: Long,
    val sellerId: Long,
    val orderTotal: BigDecimal,
    val commissionAmount: BigDecimal,
    val commissionRate: BigDecimal,
    val sellerReceives: BigDecimal,
    val paymentMethod: String,
    val status: String,
    val transactionId: String,
    val createdAt: LocalDateTime,
    val message: String
)

data class SubscriptionResponse(
    val id: Long,
    val sellerId: Long,
    val monthlyFee: BigDecimal,
    val status: String,
    val nextBillingDate: LocalDateTime,
    val message: String
)

data class PaymentSummaryResponse(
    val totalSales: BigDecimal,
    val totalCommissions: BigDecimal,
    val totalSellerEarnings: BigDecimal,
    val orderCount: Int
)
