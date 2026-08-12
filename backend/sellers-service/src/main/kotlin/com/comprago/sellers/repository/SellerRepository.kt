package com.comprago.sellers.repository

import com.comprago.sellers.model.Seller
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface SellerRepository : JpaRepository<Seller, Long> {

    fun findByEmail(email: String): Optional<Seller>

    fun findByUserId(userId: Long): Optional<Seller>

    fun existsByEmail(email: String): Boolean
}
