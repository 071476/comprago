package com.comprago.payments.service

import com.comprago.payments.dto.*
import com.comprago.payments.model.Payment
import com.comprago.payments.model.Subscription
import com.comprago.payments.repository.PaymentRepository
import com.comprago.payments.repository.SubscriptionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.util.UUID

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val subscriptionRepository: SubscriptionRepository
) {
    companion object {
        val COMMISSION_RATE = BigDecimal("3.50")
        val SUBSCRIPTION_FEE = BigDecimal("120.00")
    }

    @Transactional
    fun processPayment(request: ProcessPaymentRequest): PaymentResponse {
        val commission = request.orderTotal
            .multiply(COMMISSION_RATE)
            .divide(BigDecimal("100"), 2, RoundingMode.HALF_UP)

        val sellerReceives = request.orderTotal.subtract(commission)

        val payment = Payment(
            orderId = request.orderId,
            buyerId = request.buyerId,
            sellerId = request.sellerId,
            orderTotal = request.orderTotal,
            commissionAmount = commission,
            commissionRate = COMMISSION_RATE,
            sellerReceives = sellerReceives,
            paymentMethod = request.paymentMethod,
            status = "COMPLETED",
            transactionId = "TXN-${UUID.randomUUID().toString().substring(0, 8).uppercase()}"
        )

        val saved = paymentRepository.save(payment)
        return saved.toResponse("Pago procesado exitosamente")
    }

    fun getPaymentByOrder(orderId: Long): PaymentResponse {
        val payment = paymentRepository.findByOrderId(orderId)
            ?: throw NoSuchElementException("Pago no encontrado")
        return payment.toResponse("Pago encontrado")
    }

    fun getPaymentsBySeller(sellerId: Long): List<PaymentResponse> {
        return paymentRepository.findBySellerId(sellerId).map {
            it.toResponse("")
        }
    }

    fun getSellerSummary(sellerId: Long): PaymentSummaryResponse {
        val totalSales = paymentRepository.sumTotalSalesBySeller(sellerId)
        val totalCommissions = paymentRepository.sumCommissionsBySeller(sellerId)
        val totalEarnings = paymentRepository.sumSellerEarnings(sellerId)
        val orderCount = paymentRepository.findBySellerId(sellerId).size

        return PaymentSummaryResponse(
            totalSales = totalSales,
            totalCommissions = totalCommissions,
            totalSellerEarnings = totalEarnings,
            orderCount = orderCount
        )
    }

    @Transactional
    fun createSubscription(sellerId: Long): SubscriptionResponse {
        val existing = subscriptionRepository.findBySellerId(sellerId)
        if (existing != null) {
            throw IllegalArgumentException("El seller ya tiene una suscripción")
        }

        val subscription = Subscription(
            sellerId = sellerId,
            monthlyFee = SUBSCRIPTION_FEE,
            status = "ACTIVE",
            nextBillingDate = LocalDateTime.now().plusMonths(1)
        )

        val saved = subscriptionRepository.save(subscription)
        return saved.toResponse("Suscripción creada exitosamente")
    }

    fun getSubscription(sellerId: Long): SubscriptionResponse {
        val subscription = subscriptionRepository.findBySellerId(sellerId)
            ?: throw NoSuchElementException("Suscripción no encontrada")
        return subscription.toResponse("Suscripción encontrada")
    }

    private fun Payment.toResponse(message: String) = PaymentResponse(
        id = this.id,
        orderId = this.orderId,
        buyerId = this.buyerId,
        sellerId = this.sellerId,
        orderTotal = this.orderTotal,
        commissionAmount = this.commissionAmount,
        commissionRate = this.commissionRate,
        sellerReceives = this.sellerReceives,
        paymentMethod = this.paymentMethod,
        status = this.status,
        transactionId = this.transactionId,
        createdAt = this.createdAt,
        message = message
    )

    private fun Subscription.toResponse(message: String) = SubscriptionResponse(
        id = this.id,
        sellerId = this.sellerId,
        monthlyFee = this.monthlyFee,
        status = this.status,
        nextBillingDate = this.nextBillingDate,
        message = message
    )
}
