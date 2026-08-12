package com.comprago.customers.dto

import java.time.LocalDateTime

data class CreateCustomerRequest(
    val authUserId: Long,
    val name: String,
    val email: String,
    val phone: String,
    val defaultAddress: String = ""
)

data class UpdateCustomerRequest(
    val name: String? = null,
    val phone: String? = null,
    val defaultAddress: String? = null
)

data class AddressRequest(
    val label: String,
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val country: String = "México",
    val isDefault: Boolean = false
)

data class AddressResponse(
    val id: Long,
    val label: String,
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val country: String,
    val isDefault: Boolean
)

data class CustomerResponse(
    val id: Long,
    val authUserId: Long,
    val name: String,
    val email: String,
    val phone: String,
    val defaultAddress: String,
    val addresses: List<AddressResponse>,
    val createdAt: LocalDateTime,
    val message: String
)
