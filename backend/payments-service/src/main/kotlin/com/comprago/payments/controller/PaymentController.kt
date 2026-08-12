package com.comprago.payments.controller

import com.comprago.payments.dto.*
import com.comprago.payments.service.PaymentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/payments")
class PaymentController(private val paymentService: PaymentService) {

    @PostMapping
    fun processPayment(@RequestBody request: ProcessPaymentRequest): ResponseEntity<PaymentResponse> {
        return ResponseEntity.ok(paymentService.processPayment(request))
    }

    @GetMapping("/order/{orderId}")
    fun getPaymentByOrder(@PathVariable orderId: Long): ResponseEntity<PaymentResponse> {
        return ResponseEntity.ok(paymentService.getPaymentByOrder(orderId))
    }

    @GetMapping("/seller/{sellerId}")
    fun getPaymentsBySeller(@PathVariable sellerId: Long): ResponseEntity<List<PaymentResponse>> {
        return ResponseEntity.ok(paymentService.getPaymentsBySeller(sellerId))
    }

    @GetMapping("/seller/{sellerId}/summary")
    fun getSellerSummary(@PathVariable sellerId: Long): ResponseEntity<PaymentSummaryResponse> {
        return ResponseEntity.ok(paymentService.getSellerSummary(sellerId))
    }

    @PostMapping("/subscriptions/{sellerId}")
    fun createSubscription(@PathVariable sellerId: Long): ResponseEntity<SubscriptionResponse> {
        return ResponseEntity.ok(paymentService.createSubscription(sellerId))
    }

    @GetMapping("/subscriptions/{sellerId}")
    fun getSubscription(@PathVariable sellerId: Long): ResponseEntity<SubscriptionResponse> {
        return ResponseEntity.ok(paymentService.getSubscription(sellerId))
    }
}
