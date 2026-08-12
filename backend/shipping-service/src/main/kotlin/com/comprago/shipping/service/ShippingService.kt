package com.comprago.shipping.service

import com.comprago.shipping.dto.*
import com.comprago.shipping.model.Shipment
import com.comprago.shipping.model.ShipmentEvent
import com.comprago.shipping.repository.ShipmentEventRepository
import com.comprago.shipping.repository.ShipmentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class ShippingService(
    private val shipmentRepository: ShipmentRepository,
    private val eventRepository: ShipmentEventRepository
) {

    @Transactional
    fun createShipment(request: CreateShipmentRequest): ShipmentResponse {
        val existing = shipmentRepository.findByOrderId(request.orderId)
        if (existing != null) {
            throw IllegalArgumentException("La orden ya tiene un envío")
        }

        val trackingNumber = "CG-${UUID.randomUUID().toString().substring(0, 10).uppercase()}"

        val shipment = Shipment(
            orderId = request.orderId,
            buyerId = request.buyerId,
            sellerId = request.sellerId,
            trackingNumber = trackingNumber,
            carrier = request.carrier,
            status = "PENDING",
            shippingAddress = request.shippingAddress,
            shippingCost = request.shippingCost,
            estimatedDelivery = LocalDateTime.now().plusDays(5)
        )

        val saved = shipmentRepository.save(shipment)

        val event = ShipmentEvent(
            shipment = saved,
            status = "PENDING",
            description = "Envío creado, esperando recolección",
            location = "Almacén del vendedor"
        )
        eventRepository.save(event)
        saved.events.add(event)

        return saved.toResponse("Envío creado exitosamente")
    }

    fun getShipment(id: Long): ShipmentResponse {
        val shipment = shipmentRepository.findById(id).orElseThrow {
            NoSuchElementException("Envío no encontrado")
        }
        return shipment.toResponse("Envío encontrado")
    }

    fun getByOrderId(orderId: Long): ShipmentResponse {
        val shipment = shipmentRepository.findByOrderId(orderId)
            ?: throw NoSuchElementException("Envío no encontrado para esta orden")
        return shipment.toResponse("Envío encontrado")
    }

    fun getByTrackingNumber(trackingNumber: String): ShipmentResponse {
        val shipment = shipmentRepository.findByTrackingNumber(trackingNumber)
            ?: throw NoSuchElementException("Número de rastreo no encontrado")
        return shipment.toResponse("Envío encontrado")
    }

    fun getByBuyerId(buyerId: Long): List<ShipmentResponse> {
        return shipmentRepository.findByBuyerId(buyerId).map {
            it.toResponse("")
        }
    }

    fun getBySellerId(sellerId: Long): List<ShipmentResponse> {
        return shipmentRepository.findBySellerId(sellerId).map {
            it.toResponse("")
        }
    }

    @Transactional
    fun updateStatus(shipmentId: Long, request: UpdateStatusRequest): ShipmentResponse {
        val shipment = shipmentRepository.findById(shipmentId).orElseThrow {
            NoSuchElementException("Envío no encontrado")
        }

        shipment.status = request.status
        shipment.updatedAt = LocalDateTime.now()

        if (request.status == "DELIVERED") {
            shipment.deliveredAt = LocalDateTime.now()
        }

        val event = ShipmentEvent(
            shipment = shipment,
            status = request.status,
            description = request.description,
            location = request.location
        )
        eventRepository.save(event)
        shipment.events.add(event)

        val saved = shipmentRepository.save(shipment)
        return saved.toResponse("Estado actualizado a ${request.status}")
    }

    private fun Shipment.toResponse(message: String) = ShipmentResponse(
        id = this.id,
        orderId = this.orderId,
        buyerId = this.buyerId,
        sellerId = this.sellerId,
        trackingNumber = this.trackingNumber,
        carrier = this.carrier,
        status = this.status,
        shippingAddress = this.shippingAddress,
        shippingCost = this.shippingCost,
        estimatedDelivery = this.estimatedDelivery,
        deliveredAt = this.deliveredAt,
        events = this.events.map { event ->
            ShipmentEventResponse(
                id = event.id,
                status = event.status,
                description = event.description,
                location = event.location,
                eventTime = event.eventTime
            )
        },
        message = message
    )
}
