package com.comprago.customers.controller

import com.comprago.customers.dto.*
import com.comprago.customers.service.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/customers")
class CustomerController(private val customerService: CustomerService) {

    @PostMapping
    fun createCustomer(@RequestBody request: CreateCustomerRequest): ResponseEntity<CustomerResponse> {
        return ResponseEntity.ok(customerService.createCustomer(request))
    }

    @GetMapping("/{id}")
    fun getCustomer(@PathVariable id: Long): ResponseEntity<CustomerResponse> {
        return ResponseEntity.ok(customerService.getCustomer(id))
    }

    @GetMapping("/auth/{authUserId}")
    fun getByAuthUserId(@PathVariable authUserId: Long): ResponseEntity<CustomerResponse> {
        return ResponseEntity.ok(customerService.getByAuthUserId(authUserId))
    }

    @PutMapping("/{id}")
    fun updateCustomer(
        @PathVariable id: Long,
        @RequestBody request: UpdateCustomerRequest
    ): ResponseEntity<CustomerResponse> {
        return ResponseEntity.ok(customerService.updateCustomer(id, request))
    }

    @PostMapping("/{id}/addresses")
    fun addAddress(
        @PathVariable id: Long,
        @RequestBody request: AddressRequest
    ): ResponseEntity<CustomerResponse> {
        return ResponseEntity.ok(customerService.addAddress(id, request))
    }

    @GetMapping("/{id}/addresses")
    fun getAddresses(@PathVariable id: Long): ResponseEntity<List<AddressResponse>> {
        return ResponseEntity.ok(customerService.getAddresses(id))
    }

    @DeleteMapping("/{customerId}/addresses/{addressId}")
    fun removeAddress(
        @PathVariable customerId: Long,
        @PathVariable addressId: Long
    ): ResponseEntity<CustomerResponse> {
        return ResponseEntity.ok(customerService.removeAddress(customerId, addressId))
    }
}
