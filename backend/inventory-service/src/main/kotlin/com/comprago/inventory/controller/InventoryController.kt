package com.comprago.inventory.controller

import com.comprago.inventory.dto.CreateInventoryRequest
import com.comprago.inventory.dto.InventoryResponse
import com.comprago.inventory.dto.UpdateStockRequest
import com.comprago.inventory.service.InventoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/inventory")
class InventoryController(private val inventoryService: InventoryService) {

    @PostMapping
    fun createItem(@RequestBody request: CreateInventoryRequest): ResponseEntity<InventoryResponse> {
        return ResponseEntity.ok(inventoryService.createItem(request))
    }

    @GetMapping("/{id}")
    fun getItem(@PathVariable id: Long): ResponseEntity<InventoryResponse> {
        return ResponseEntity.ok(inventoryService.getItem(id))
    }

    @GetMapping
    fun getAllItems(): ResponseEntity<List<InventoryResponse>> {
        return ResponseEntity.ok(inventoryService.getAllItems())
    }

    @GetMapping("/seller/{sellerId}")
    fun getItemsBySeller(@PathVariable sellerId: Long): ResponseEntity<List<InventoryResponse>> {
        return ResponseEntity.ok(inventoryService.getItemsBySeller(sellerId))
    }

    @GetMapping("/low-stock")
    fun getLowStockItems(): ResponseEntity<List<InventoryResponse>> {
        return ResponseEntity.ok(inventoryService.getLowStockItems())
    }

    @PutMapping("/{id}/stock")
    fun updateStock(
        @PathVariable id: Long,
        @RequestBody request: UpdateStockRequest
    ): ResponseEntity<InventoryResponse> {
        return ResponseEntity.ok(inventoryService.updateStock(id, request.stock))
    }

    @DeleteMapping("/{id}")
    fun deleteItem(@PathVariable id: Long): ResponseEntity<Void> {
        inventoryService.deleteItem(id)
        return ResponseEntity.ok().build()
    }
}
