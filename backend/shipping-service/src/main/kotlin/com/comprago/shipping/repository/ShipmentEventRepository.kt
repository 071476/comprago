package com.comprago.shipping.repository

import com.comprago.shipping.model.ShipmentEvent
import org.springframework.data.jpa.repository.JpaRepository

interface ShipmentEventRepository : JpaRepository<ShipmentEvent, Long> {
    fun findByShipmentId(shipmentId: Long): List<ShipmentEvent>
}
