package com.comprago.sellers.repository

import com.comprago.sellers.model.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface StoreRepository : JpaRepository<Store, Long> {

    fun findBySellerId(sellerId: Long): Optional<Store>

    fun findAllBySellerId(sellerId: Long): List<Store>
}
