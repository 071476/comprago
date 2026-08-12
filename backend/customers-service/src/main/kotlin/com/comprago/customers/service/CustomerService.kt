package com.comprago.customers.service

import com.comprago.customers.dto.*
import com.comprago.customers.model.Address
import com.comprago.customers.model.Customer
import com.comprago.customers.repository.AddressRepository
import com.comprago.customers.repository.CustomerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val addressRepository: AddressRepository
) {

    @Transactional
    fun createCustomer(request: CreateCustomerRequest): CustomerResponse {
        val existing = customerRepository.findByAuthUserId(request.authUserId)
        if (existing != null) {
            throw IllegalArgumentException("El cliente ya existe")
        }

        val customer = Customer(
            authUserId = request.authUserId,
            name = request.name,
            email = request.email,
            phone = request.phone,
            defaultAddress = request.defaultAddress
        )

        val saved = customerRepository.save(customer)
        return saved.toResponse("Cliente creado exitosamente")
    }

    fun getCustomer(id: Long): CustomerResponse {
        val customer = customerRepository.findById(id).orElseThrow {
            NoSuchElementException("Cliente no encontrado")
        }
        return customer.toResponse("Cliente encontrado")
    }

    fun getByAuthUserId(authUserId: Long): CustomerResponse {
        val customer = customerRepository.findByAuthUserId(authUserId)
            ?: throw NoSuchElementException("Cliente no encontrado")
        return customer.toResponse("Cliente encontrado")
    }

    @Transactional
    fun updateCustomer(id: Long, request: UpdateCustomerRequest): CustomerResponse {
        val customer = customerRepository.findById(id).orElseThrow {
            NoSuchElementException("Cliente no encontrado")
        }

        request.name?.let { customer.name = it }
        request.phone?.let { customer.phone = it }
        request.defaultAddress?.let { customer.defaultAddress = it }
        customer.updatedAt = LocalDateTime.now()

        val saved = customerRepository.save(customer)
        return saved.toResponse("Cliente actualizado")
    }

    @Transactional
    fun addAddress(customerId: Long, request: AddressRequest): CustomerResponse {
        val customer = customerRepository.findById(customerId).orElseThrow {
            NoSuchElementException("Cliente no encontrado")
        }

        if (request.isDefault) {
            val currentDefault = addressRepository.findByCustomerIdAndIsDefaultTrue(customerId)
            if (currentDefault != null) {
                currentDefault.isDefault = false
                addressRepository.save(currentDefault)
            }
        }

        val address = Address(
            customer = customer,
            label = request.label,
            street = request.street,
            city = request.city,
            state = request.state,
            zipCode = request.zipCode,
            country = request.country,
            isDefault = request.isDefault
        )

        addressRepository.save(address)
        customer.addresses.add(address)
        customer.updatedAt = LocalDateTime.now()
        customerRepository.save(customer)

        return customer.toResponse("Dirección agregada")
    }

    @Transactional
    fun removeAddress(customerId: Long, addressId: Long): CustomerResponse {
        val customer = customerRepository.findById(customerId).orElseThrow {
            NoSuchElementException("Cliente no encontrado")
        }

        val address = addressRepository.findById(addressId).orElseThrow {
            NoSuchElementException("Dirección no encontrada")
        }

        if (address.customer.id != customerId) {
            throw IllegalArgumentException("La dirección no pertenece a este cliente")
        }

        addressRepository.delete(address)
        customer.addresses.remove(address)
        customer.updatedAt = LocalDateTime.now()
        customerRepository.save(customer)

        return customer.toResponse("Dirección eliminada")
    }

    fun getAddresses(customerId: Long): List<AddressResponse> {
        return addressRepository.findByCustomerId(customerId).map { it.toResponse() }
    }

    private fun Customer.toResponse(message: String) = CustomerResponse(
        id = this.id,
        authUserId = this.authUserId,
        name = this.name,
        email = this.email,
        phone = this.phone,
        defaultAddress = this.defaultAddress,
        addresses = this.addresses.map { it.toResponse() },
        createdAt = this.createdAt,
        message = message
    )

    private fun Address.toResponse() = AddressResponse(
        id = this.id,
        label = this.label,
        street = this.street,
        city = this.city,
        state = this.state,
        zipCode = this.zipCode,
        country = this.country,
        isDefault = this.isDefault
    )
}
