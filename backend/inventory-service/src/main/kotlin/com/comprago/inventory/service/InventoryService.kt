package com.comprago.inventory.service

import com.comprago.inventory.dto.*
import com.comprago.inventory.model.InventoryItem
import com.comprago.inventory.repository.InventoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class InventoryService(private val repository: InventoryRepository) {

    fun createInventory(request: CreateInventoryRequest): InventoryResponse {
        val existing = repository.findByProductId(request.productId)
        if (existing != null) {
            throw IllegalArgumentException("El producto ya tiene inventario")
        }
        val item = InventoryItem(
            productId = request.productId,
            stock = request.stock
        )
        val saved = repository.save(item)
        return saved.toResponse("Inventario creado exitosamente")
    }

    fun getByProductId(productId: Long): InventoryResponse {
        val item = repository.findByProductId(productId) ?: throw NoSuchElementException("Inventario no encontrado")
        return item.toResponse("Inventario encontrado")
    }

    @Transactional
    fun addStock(productId: Long, request: UpdateStockRequest): InventoryResponse {
        val item = repository.findByProductId(productId) ?: throw NoSuchElementException("Inventario no encontrado")
        item.stock += request.quantity
        item.updatedAt = LocalDateTime.now()
        val saved = repository.save(item)
        return saved.toResponse("Stock actualizado")
    }

    @Transactional
    fun reserveStock(productId: Long, request: ReserveStockRequest): InventoryResponse {
        val item = repository.findByProductId(productId) ?: throw NoSuchElementException("Inventario no encontrado")
        if (item.available < request.quantity) {
            throw IllegalStateException("Stock insuficiente. Disponible: ${item.available}")
        }
        item.reserved += request.quantity
        item.updatedAt = LocalDateTime.now()
        val saved = repository.save(item)
        return saved.toResponse("Stock reservado exitosamente")
    }

    @Transactional
    fun confirmReservation(productId: Long, request: ReserveStockRequest): InventoryResponse {
        val item = repository.findByProductId(productId) ?: throw NoSuchElementException("Inventario no encontrado")
        item.stock -= request.quantity
        item.reserved -= request.quantity
        item.updatedAt = LocalDateTime.now()
        val saved = repository.save(item)
        return saved.toResponse("Reservación confirmada")
    }

    @Transactional
    fun releaseStock(productId: Long, request: ReserveStockRequest): InventoryResponse {
        val item = repository.findByProductId(productId) ?: throw NoSuchElementException("Inventario no encontrado")
        item.reserved -= request.quantity
        item.updatedAt = LocalDateTime.now()
        val saved = repository.save(item)
        return saved.toResponse("Stock liberado")
    }

    private fun InventoryItem.toResponse(message: String) = InventoryResponse(
        id = this.id,
        productId = this.productId,
        stock = this.stock,
        reserved = this.reserved,
        available = this.available,
        message = message
    )
}
