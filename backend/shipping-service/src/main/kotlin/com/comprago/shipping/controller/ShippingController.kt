package com.comprago.shipping.controller

import com.comprago.shipping.dto.*
import com.comprago.shipping.service.ShippingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/shipping")
class ShippingController(private val shippingService: ShippingService) {

    @PostMapping
    fun createShipment(@RequestBody request: CreateShipmentRequest): ResponseEntity<ShipmentResponse> {
        return ResponseEntity.ok(shippingService.createShipment(request))
    }

    @GetMapping("/{id}")
    fun getShipment(@PathVariable id: Long): ResponseEntity<ShipmentResponse> {
        return ResponseEntity.ok(shippingService.getShipment(id))
    }

    @GetMapping("/order/{orderId}")
    fun getByOrderId(@PathVariable orderId: Long): ResponseEntity<ShipmentResponse> {
        return ResponseEntity.ok(shippingService.getByOrderId(orderId))
    }

    @GetMapping("/track/{trackingNumber}")
    fun getByTrackingNumber(@PathVariable trackingNumber: String): ResponseEntity<ShipmentResponse> {
        return ResponseEntity.ok(shippingService.getByTrackingNumber(trackingNumber))
    }

    @GetMapping("/buyer/{buyerId}")
    fun getByBuyerId(@PathVariable buyerId: Long): ResponseEntity<List<ShipmentResponse>> {
        return ResponseEntity.ok(shippingService.getByBuyerId(buyerId))
    }

    @GetMapping("/seller/{sellerId}")
    fun getBySellerId(@PathVariable sellerId: Long): ResponseEntity<List<ShipmentResponse>> {
        return ResponseEntity.ok(shippingService.getBySellerId(sellerId))
    }

    @PatchMapping("/{id}/status")
    fun updateStatus(
        @PathVariable id: Long,
        @RequestBody request: UpdateStatusRequest
    ): ResponseEntity<ShipmentResponse> {
        return ResponseEntity.ok(shippingService.updateStatus(id, request))
    }
}
