package com.comprago.customers.repository

import com.comprago.customers.model.Address
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository : JpaRepository<Address, Long> {
    fun findByCustomerId(customerId: Long): List<Address>
    fun findByCustomerIdAndIsDefaultTrue(customerId: Long): Address?
    fun deleteByCustomerId(customerId: Long)
}
