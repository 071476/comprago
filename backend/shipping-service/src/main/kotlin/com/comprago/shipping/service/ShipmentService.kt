package com.comprago.shipping.service

import com.comprago.shipping.dto.CreateShipmentRequest
import com.comprago.shipping.dto.ShipmentResponse
import com.comprago.shipping.entity.Shipment
import com.comprago.shipping.repository.ShipmentRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class ShipmentService(private val shipmentRepository: ShipmentRepository) {

    fun createShipment(request: CreateShipmentRequest): ShipmentResponse {
        val trackingNumber = "CG-${UUID.randomUUID().toString().take(8).uppercase()}"
        val shipment = Shipment(
            orderId = request.orderId,
            sellerId = request.sellerId,
            buyerId = request.buyerId,
            trackingNumber = trackingNumber,
            carrier = request.carrier,
            status = "PENDING",
            originAddress = request.originAddress,
            destinationAddress = request.destinationAddress,
            estimatedDelivery = request.estimatedDelivery
        )
        return shipmentRepository.save(shipment).toResponse()
    }

    fun getShipment(id: Long): ShipmentResponse {
        val shipment = shipmentRepository.findById(id)
            .orElseThrow { RuntimeException("Shipment not found: $id") }
        return shipment.toResponse()
    }

    fun getAllShipments(): List<ShipmentResponse> {
        return shipmentRepository.findAll().map { it.toResponse() }
    }

    fun getShipmentsBySeller(sellerId: Long): List<ShipmentResponse> {
        return shipmentRepository.findBySellerId(sellerId).map { it.toResponse() }
    }

    fun getShipmentsByBuyer(buyerId: Long): List<ShipmentResponse> {
        return shipmentRepository.findByBuyerId(buyerId).map { it.toResponse() }
    }

    fun getShipmentByOrder(orderId: Long): ShipmentResponse? {
        return shipmentRepository.findByOrderId(orderId)?.toResponse()
    }

    fun updateStatus(id: Long, status: String): ShipmentResponse {
        val shipment = shipmentRepository.findById(id)
            .orElseThrow { RuntimeException("Shipment not found: $id") }
        val updated = shipment.copy(status = status, updatedAt = LocalDateTime.now())
        return shipmentRepository.save(updated).toResponse()
    }

    fun deleteShipment(id: Long) {
        shipmentRepository.deleteById(id)
    }

    private fun Shipment.toResponse(): ShipmentResponse {
        return ShipmentResponse(
            id = id,
            orderId = orderId,
            sellerId = sellerId,
            buyerId = buyerId,
            trackingNumber = trackingNumber,
            carrier = carrier,
            status = status,
            originAddress = originAddress,
            destinationAddress = destinationAddress,
            estimatedDelivery = estimatedDelivery,
            createdAt = createdAt.toString(),
            updatedAt = updatedAt.toString()
        )
    }
}
