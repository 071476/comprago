package com.comprago.sellers.service

import com.comprago.sellers.dto.SellerRegisterRequest
import com.comprago.sellers.dto.SellerResponse
import com.comprago.sellers.dto.StoreCreateRequest
import com.comprago.sellers.dto.StoreResponse
import com.comprago.sellers.model.Seller
import com.comprago.sellers.model.Store
import com.comprago.sellers.repository.SellerRepository
import com.comprago.sellers.repository.StoreRepository
import org.springframework.stereotype.Service

@Service
class SellerService(
    private val sellerRepository: SellerRepository,
    private val storeRepository: StoreRepository
) {

    fun registerSeller(request: SellerRegisterRequest): SellerResponse {

        if (sellerRepository.existsByEmail(request.email)) {
            throw RuntimeException("El email ya esta registrado como vendedor")
        }

        val seller = Seller(
            userId = request.userId,
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            phone = request.phone
        )

        sellerRepository.save(seller)

        return SellerResponse(
            id = seller.id,
            firstName = seller.firstName,
            lastName = seller.lastName,
            email = seller.email,
            phone = seller.phone,
            status = seller.status.name,
            message = "Vendedor registrado exitosamente"
        )
    }

    fun getSeller(id: Long): SellerResponse {
        val seller = sellerRepository.findById(id)
            .orElseThrow { RuntimeException("Vendedor no encontrado") }

        return SellerResponse(
            id = seller.id,
            firstName = seller.firstName,
            lastName = seller.lastName,
            email = seller.email,
            phone = seller.phone,
            status = seller.status.name,
            message = "Vendedor encontrado"
        )
    }

    fun createStore(request: StoreCreateRequest): StoreResponse {

        val seller = sellerRepository.findById(request.sellerId)
            .orElseThrow { RuntimeException("Vendedor no encontrado") }

        val store = Store(
            sellerId = seller.id,
            name = request.name,
            description = request.description,
            category = request.category,
            logoUrl = request.logoUrl,
            bannerUrl = request.bannerUrl
        )

        storeRepository.save(store)

        return StoreResponse(
            id = store.id,
            name = store.name,
            description = store.description,
            category = store.category,
            logoUrl = store.logoUrl,
            bannerUrl = store.bannerUrl,
            active = store.active,
            message = "Tienda creada exitosamente"
        )
    }

    fun getStoreBySellerId(sellerId: Long): StoreResponse {
        val store = storeRepository.findBySellerId(sellerId)
            .orElseThrow { RuntimeException("Tienda no encontrada") }

        return StoreResponse(
            id = store.id,
            name = store.name,
            description = store.description,
            category = store.category,
            logoUrl = store.logoUrl,
            bannerUrl = store.bannerUrl,
            active = store.active,
            message = "Tienda encontrada"
        )
    }
}
