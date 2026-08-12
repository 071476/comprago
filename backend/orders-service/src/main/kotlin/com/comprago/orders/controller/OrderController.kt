package com.comprago.orders.controller

import com.comprago.orders.dto.*
import com.comprago.orders.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrderController(private val orderService: OrderService) {

    @PostMapping
    fun createOrder(
        @RequestHeader("X-Buyer-Id") buyerId: Long,
        @RequestBody request: CreateOrderRequest
    ): ResponseEntity<OrderResponse> {
        return ResponseEntity.ok(orderService.createOrder(buyerId, request))
    }

    @GetMapping("/{id}")
    fun getOrder(@PathVariable id: Long): ResponseEntity<OrderResponse> {
        return ResponseEntity.ok(orderService.getOrder(id))
    }

    @GetMapping("/buyer/{buyerId}")
    fun getOrdersByBuyer(@PathVariable buyerId: Long): ResponseEntity<List<OrderResponse>> {
        return ResponseEntity.ok(orderService.getOrdersByBuyer(buyerId))
    }

    @GetMapping("/seller/{sellerId}")
    fun getOrdersBySeller(@PathVariable sellerId: Long): ResponseEntity<List<OrderResponse>> {
        return ResponseEntity.ok(orderService.getOrdersBySeller(sellerId))
    }

    @PatchMapping("/{id}/status")
    fun updateStatus(
        @PathVariable id: Long,
        @RequestBody request: UpdateStatusRequest
    ): ResponseEntity<OrderResponse> {
        return ResponseEntity.ok(orderService.updateStatus(id, request))
    }
}
