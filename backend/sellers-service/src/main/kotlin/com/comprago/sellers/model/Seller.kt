package com.comprago.sellers.model

import jakarta.persistence.*

enum class SellerStatus {
    PENDING,
    APPROVED,
    REJECTED
}

@Entity
@Table(name = "sellers")
class Seller(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var userId: Long = 0,

    @Column(nullable = false)
    var firstName: String = "",

    @Column(nullable = false)
    var lastName: String = "",

    @Column(unique = true, nullable = false)
    var email: String = "",

    @Column(nullable = false)
    var phone: String = "",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: SellerStatus = SellerStatus.PENDING
)
