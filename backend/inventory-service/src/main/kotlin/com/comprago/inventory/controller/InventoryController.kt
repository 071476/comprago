package com.comprago.inventory.controller

import com.comprago.inventory.dto.*
import com.comprago.inventory.service.InventoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/inventory")
class InventoryController(private val inventoryService: InventoryService) {

    @PostMapping
    fun createInventory(@RequestBody request: CreateInventoryRequest): ResponseEntity<InventoryResponse> {
        return ResponseEntity.ok(inventoryService.createInventory(request))
    }

    @GetMapping("/product/{productId}")
    fun getByProductId(@PathVariable productId: Long): ResponseEntity<InventoryResponse> {
        return ResponseEntity.ok(inventoryService.getByProductId(productId))
    }

    @PostMapping("/product/{productId}/add")
    fun addStock(@PathVariable productId: Long, @RequestBody request: UpdateStockRequest): ResponseEntity<InventoryResponse> {
        return ResponseEntity.ok(inventoryService.addStock(productId, request))
    }

    @PostMapping("/product/{productId}/reserve")
    fun reserveStock(@PathVariable productId: Long, @RequestBody request: ReserveStockRequest): ResponseEntity<InventoryResponse> {
        return ResponseEntity.ok(inventoryService.reserveStock(productId, request))
    }

    @PostMapping("/product/{productId}/confirm")
    fun confirmReservation(@PathVariable productId: Long, @RequestBody request: ReserveStockRequest): ResponseEntity<InventoryResponse> {
        return ResponseEntity.ok(inventoryService.confirmReservation(productId, request))
    }

    @PostMapping("/product/{productId}/release")
    fun releaseStock(@PathVariable productId: Long, @RequestBody request: ReserveStockRequest): ResponseEntity<InventoryResponse> {
        return ResponseEntity.ok(inventoryService.releaseStock(productId, request))
    }
}
