package com.comprago.shipping.repository

import com.comprago.shipping.model.Shipment
import org.springframework.data.jpa.repository.JpaRepository

interface ShipmentRepository : JpaRepository<Shipment, Long> {
    fun findByOrderId(orderId: Long): Shipment?
    fun findByTrackingNumber(trackingNumber: String): Shipment?
    fun findByBuyerId(buyerId: Long): List<Shipment>
    fun findBySellerId(sellerId: Long): List<Shipment>
    fun findByStatus(status: String): List<Shipment>
}
