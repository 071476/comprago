package com.comprago.inventory.repository

import com.comprago.inventory.entity.InventoryItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InventoryRepository : JpaRepository<InventoryItem, Long> {
    fun findBySellerId(sellerId: Long): List<InventoryItem>
    fun findByProductId(productId: Long): InventoryItem?
    fun findByStockLessThan(minStock: Int): List<InventoryItem>
}
