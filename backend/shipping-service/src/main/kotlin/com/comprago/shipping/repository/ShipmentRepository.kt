package com.comprago.shipping.repository

import com.comprago.shipping.entity.Shipment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ShipmentRepository : JpaRepository<Shipment, Long> {
    fun findBySellerId(sellerId: Long): List<Shipment>
    fun findByBuyerId(buyerId: Long): List<Shipment>
    fun findByOrderId(orderId: Long): Shipment?
    fun findByTrackingNumber(trackingNumber: String): Shipment?
}
