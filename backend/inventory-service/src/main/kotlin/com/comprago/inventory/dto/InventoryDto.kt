package com.comprago.inventory.dto

data class CreateInventoryRequest(
    val productId: Long,
    val sellerId: Long,
    val productName: String,
    val stock: Int,
    val minStock: Int = 5,
    val maxStock: Int = 1000
)

data class UpdateStockRequest(
    val stock: Int
)

data class InventoryResponse(
    val id: Long,
    val productId: Long,
    val sellerId: Long,
    val productName: String,
    val stock: Int,
    val minStock: Int,
    val maxStock: Int,
    val lowStock: Boolean,
    val createdAt: String,
    val updatedAt: String
)
