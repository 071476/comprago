package com.comprago.shipping.controller

import com.comprago.shipping.dto.CreateShipmentRequest
import com.comprago.shipping.dto.ShipmentResponse
import com.comprago.shipping.dto.UpdateShipmentStatusRequest
import com.comprago.shipping.service.ShipmentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/shipping")
class ShipmentController(private val shipmentService: ShipmentService) {

    @PostMapping
    fun createShipment(@RequestBody request: CreateShipmentRequest): ResponseEntity<ShipmentResponse> {
        return ResponseEntity.ok(shipmentService.createShipment(request))
    }

    @GetMapping("/{id}")
    fun getShipment(@PathVariable id: Long): ResponseEntity<ShipmentResponse> {
        return ResponseEntity.ok(shipmentService.getShipment(id))
    }

    @GetMapping
    fun getAllShipments(): ResponseEntity<List<ShipmentResponse>> {
        return ResponseEntity.ok(shipmentService.getAllShipments())
    }

    @GetMapping("/seller/{sellerId}")
    fun getShipmentsBySeller(@PathVariable sellerId: Long): ResponseEntity<List<ShipmentResponse>> {
        return ResponseEntity.ok(shipmentService.getShipmentsBySeller(sellerId))
    }

    @GetMapping("/buyer/{buyerId}")
    fun getShipmentsByBuyer(@PathVariable buyerId: Long): ResponseEntity<List<ShipmentResponse>> {
        return ResponseEntity.ok(shipmentService.getShipmentsByBuyer(buyerId))
    }

    @GetMapping("/order/{orderId}")
    fun getShipmentByOrder(@PathVariable orderId: Long): ResponseEntity<ShipmentResponse?> {
        return ResponseEntity.ok(shipmentService.getShipmentByOrder(orderId))
    }

    @PutMapping("/{id}/status")
    fun updateStatus(
        @PathVariable id: Long,
        @RequestBody request: UpdateShipmentStatusRequest
    ): ResponseEntity<ShipmentResponse> {
        return ResponseEntity.ok(shipmentService.updateStatus(id, request.status))
    }

    @DeleteMapping("/{id}")
    fun deleteShipment(@PathVariable id: Long): ResponseEntity<Void> {
        shipmentService.deleteShipment(id)
        return ResponseEntity.ok().build()
    }
}
