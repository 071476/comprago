package com.comprago.shipping.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "shipment_events")
class ShipmentEvent(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id", nullable = false)
    var shipment: Shipment = Shipment(),

    @Column(nullable = false)
    var status: String = "",

    @Column(columnDefinition = "TEXT")
    var description: String = "",

    @Column(name = "location")
    var location: String = "",

    @Column(name = "event_time")
    var eventTime: LocalDateTime = LocalDateTime.now()
)
