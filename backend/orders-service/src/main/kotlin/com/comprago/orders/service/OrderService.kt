package com.comprago.orders.service

import com.comprago.orders.dto.CreateOrderRequest
import com.comprago.orders.dto.OrderResponse
import com.comprago.orders.entity.Order
import com.comprago.orders.repository.OrderRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderService(private val orderRepository: OrderRepository) {

    fun createOrder(request: CreateOrderRequest): OrderResponse {
        val order = Order(
            buyerId = request.buyerId,
            sellerId = request.sellerId,
            storeId = request.storeId,
            productId = request.productId,
            productName = request.productName,
            quantity = request.quantity,
            unitPrice = request.unitPrice,
            totalAmount = request.unitPrice.multiply(java.math.BigDecimal(request.quantity)),
            status = "PENDING",
            shippingAddress = request.shippingAddress
        )
        return orderRepository.save(order).toResponse()
    }

    fun getOrder(id: Long): OrderResponse {
        val order = orderRepository.findById(id)
            .orElseThrow { RuntimeException("Order not found: $id") }
        return order.toResponse()
    }

    fun getAllOrders(): List<OrderResponse> {
        return orderRepository.findAll().map { it.toResponse() }
    }

    fun getOrdersBySeller(sellerId: Long): List<OrderResponse> {
        return orderRepository.findBySellerId(sellerId).map { it.toResponse() }
    }

    fun getOrdersByBuyer(buyerId: Long): List<OrderResponse> {
        return orderRepository.findByBuyerId(buyerId).map { it.toResponse() }
    }

    fun updateOrderStatus(id: Long, status: String): OrderResponse {
        val order = orderRepository.findById(id)
            .orElseThrow { RuntimeException("Order not found: $id") }
        val updated = order.copy(status = status, updatedAt = LocalDateTime.now())
        return orderRepository.save(updated).toResponse()
    }

    fun deleteOrder(id: Long) {
        orderRepository.deleteById(id)
    }

    private fun Order.toResponse(): OrderResponse {
        return OrderResponse(
            id = id,
            buyerId = buyerId,
            sellerId = sellerId,
            storeId = storeId,
            productId = productId,
            productName = productName,
            quantity = quantity,
            unitPrice = unitPrice,
            totalAmount = totalAmount,
            status = status,
            shippingAddress = shippingAddress,
            createdAt = createdAt.toString(),
            updatedAt = updatedAt.toString()
        )
    }
}
