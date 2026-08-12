package com.comprago.orders.service

import com.comprago.orders.dto.*
import com.comprago.orders.model.Order
import com.comprago.orders.model.OrderItem
import com.comprago.orders.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class OrderService(private val repository: OrderRepository) {

    @Transactional
    fun createOrder(buyerId: Long, request: CreateOrderRequest): OrderResponse {
        val order = Order(
            buyerId = buyerId,
            sellerId = request.sellerId,
            storeId = request.storeId,
            shippingAddress = request.shippingAddress,
            paymentMethod = request.paymentMethod,
            status = "PENDING"
        )

        var total = BigDecimal.ZERO
        val orderItems = request.items.map { itemRequest ->
            val subtotal = itemRequest.price.multiply(BigDecimal(itemRequest.quantity))
            total += subtotal
            OrderItem(
                order = order,
                productId = itemRequest.productId,
                productName = itemRequest.productName,
                price = itemRequest.price,
                quantity = itemRequest.quantity,
                imageUrl = itemRequest.imageUrl
            )
        }

        order.items = orderItems.toMutableList()
        order.total = total
        order.updatedAt = LocalDateTime.now()

        val saved = repository.save(order)
        return saved.toResponse("Orden creada exitosamente")
    }

    fun getOrder(id: Long): OrderResponse {
        val order = repository.findById(id).orElseThrow {
            NoSuchElementException("Orden no encontrada")
        }
        return order.toResponse("Orden encontrada")
    }

    fun getOrdersByBuyer(buyerId: Long): List<OrderResponse> {
        return repository.findByBuyerId(buyerId).map {
            it.toResponse("")
        }
    }

    fun getOrdersBySeller(sellerId: Long): List<OrderResponse> {
        return repository.findBySellerId(sellerId).map {
            it.toResponse("")
        }
    }

    @Transactional
    fun updateStatus(orderId: Long, request: UpdateStatusRequest): OrderResponse {
        val order = repository.findById(orderId).orElseThrow {
            NoSuchElementException("Orden no encontrada")
        }
        order.status = request.status
        order.updatedAt = LocalDateTime.now()
        val saved = repository.save(order)
        return saved.toResponse("Estado actualizado a ${request.status}")
    }

    private fun Order.toResponse(message: String) = OrderResponse(
        id = this.id,
        buyerId = this.buyerId,
        sellerId = this.sellerId,
        storeId = this.storeId,
        items = this.items.map { item ->
            OrderItemResponse(
                id = item.id,
                productId = item.productId,
                productName = item.productName,
                price = item.price,
                quantity = item.quantity,
                subtotal = item.subtotal,
                imageUrl = item.imageUrl
            )
        },
        total = this.total,
        status = this.status,
        shippingAddress = this.shippingAddress,
        paymentMethod = this.paymentMethod,
        createdAt = this.createdAt,
        message = message
    )
}
