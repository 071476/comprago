package com.comprago.customers.repository

import com.comprago.customers.model.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, Long> {
    fun findByAuthUserId(authUserId: Long): Customer?
    fun findByEmail(email: String): Customer?
}
