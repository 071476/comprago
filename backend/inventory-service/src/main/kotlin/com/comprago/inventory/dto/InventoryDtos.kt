package com.comprago.inventory.dto

data class CreateInventoryRequest(
    val productId: Long,
    val stock: Int
)

data class UpdateStockRequest(
    val quantity: Int
)

data class ReserveStockRequest(
    val quantity: Int
)

data class InventoryResponse(
    val id: Long,
    val productId: Long,
    val stock: Int,
    val reserved: Int,
    val available: Int,
    val message: String
)
