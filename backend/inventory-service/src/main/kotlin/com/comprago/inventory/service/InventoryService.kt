package com.comprago.inventory.service

import com.comprago.inventory.dto.CreateInventoryRequest
import com.comprago.inventory.dto.InventoryResponse
import com.comprago.inventory.entity.InventoryItem
import com.comprago.inventory.repository.InventoryRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class InventoryService(private val inventoryRepository: InventoryRepository) {

    fun createItem(request: CreateInventoryRequest): InventoryResponse {
        val item = InventoryItem(
            productId = request.productId,
            sellerId = request.sellerId,
            productName = request.productName,
            stock = request.stock,
            minStock = request.minStock,
            maxStock = request.maxStock
        )
        return inventoryRepository.save(item).toResponse()
    }

    fun getItem(id: Long): InventoryResponse {
        val item = inventoryRepository.findById(id)
            .orElseThrow { RuntimeException("Inventory item not found: $id") }
        return item.toResponse()
    }

    fun getAllItems(): List<InventoryResponse> {
        return inventoryRepository.findAll().map { it.toResponse() }
    }

    fun getItemsBySeller(sellerId: Long): List<InventoryResponse> {
        return inventoryRepository.findBySellerId(sellerId).map { it.toResponse() }
    }

    fun updateStock(id: Long, stock: Int): InventoryResponse {
        val item = inventoryRepository.findById(id)
            .orElseThrow { RuntimeException("Inventory item not found: $id") }
        val updated = item.copy(stock = stock, updatedAt = LocalDateTime.now())
        return inventoryRepository.save(updated).toResponse()
    }

    fun getLowStockItems(): List<InventoryResponse> {
        return inventoryRepository.findAll()
            .filter { it.stock <= it.minStock }
            .map { it.toResponse() }
    }

    fun deleteItem(id: Long) {
        inventoryRepository.deleteById(id)
    }

    private fun InventoryItem.toResponse(): InventoryResponse {
        return InventoryResponse(
            id = id,
            productId = productId,
            sellerId = sellerId,
            productName = productName,
            stock = stock,
            minStock = minStock,
            maxStock = maxStock,
            lowStock = stock <= minStock,
            createdAt = createdAt.toString(),
            updatedAt = updatedAt.toString()
        )
    }
}
