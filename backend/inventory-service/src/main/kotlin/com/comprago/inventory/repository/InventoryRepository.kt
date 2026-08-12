package com.comprago.inventory.repository

import com.comprago.inventory.model.InventoryItem
import org.springframework.data.jpa.repository.JpaRepository

interface InventoryRepository : JpaRepository<InventoryItem, Long> {
    fun findByProductId(productId: Long): InventoryItem?
}
