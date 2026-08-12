package com.comprago.sellers.model

import jakarta.persistence.*

@Entity
@Table(name = "stores")
class Store(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var sellerId: Long = 0,

    @Column(nullable = false)
    var name: String = "",

    @Column(length = 500)
    var description: String = "",

    @Column(nullable = false)
    var category: String = "",

    var logoUrl: String = "",

    var bannerUrl: String = "",

    @Column(nullable = false)
    var active: Boolean = true
)
