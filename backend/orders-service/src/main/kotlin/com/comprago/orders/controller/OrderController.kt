package com.comprago.orders.controller

import com.comprago.orders.dto.CreateOrderRequest
import com.comprago.orders.dto.OrderResponse
import com.comprago.orders.dto.UpdateOrderStatusRequest
import com.comprago.orders.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrderController(private val orderService: OrderService) {

    @PostMapping
    fun createOrder(@RequestBody request: CreateOrderRequest): ResponseEntity<OrderResponse> {
        return ResponseEntity.ok(orderService.createOrder(request))
    }

    @GetMapping("/{id}")
    fun getOrder(@PathVariable id: Long): ResponseEntity<OrderResponse> {
        return ResponseEntity.ok(orderService.getOrder(id))
    }

    @GetMapping
    fun getAllOrders(): ResponseEntity<List<OrderResponse>> {
        return ResponseEntity.ok(orderService.getAllOrders())
    }

    @GetMapping("/seller/{sellerId}")
    fun getOrdersBySeller(@PathVariable sellerId: Long): ResponseEntity<List<OrderResponse>> {
        return ResponseEntity.ok(orderService.getOrdersBySeller(sellerId))
    }

    @GetMapping("/buyer/{buyerId}")
    fun getOrdersByBuyer(@PathVariable buyerId: Long): ResponseEntity<List<OrderResponse>> {
        return ResponseEntity.ok(orderService.getOrdersByBuyer(buyerId))
    }

    @PutMapping("/{id}/status")
    fun updateStatus(
        @PathVariable id: Long,
        @RequestBody request: UpdateOrderStatusRequest
    ): ResponseEntity<OrderResponse> {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, request.status))
    }

    @DeleteMapping("/{id}")
    fun deleteOrder(@PathVariable id: Long): ResponseEntity<Void> {
        orderService.deleteOrder(id)
        return ResponseEntity.ok().build()
    }
}
