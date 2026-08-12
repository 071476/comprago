package com.comprago.sellers.controller

import com.comprago.sellers.dto.SellerRegisterRequest
import com.comprago.sellers.dto.SellerResponse
import com.comprago.sellers.dto.StoreCreateRequest
import com.comprago.sellers.dto.StoreResponse
import com.comprago.sellers.service.SellerService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sellers")
class SellerController(
    private val sellerService: SellerService
) {

    @PostMapping("/register")
    fun registerSeller(
        @Valid @RequestBody request: SellerRegisterRequest
    ): ResponseEntity<SellerResponse> {
        val response = sellerService.registerSeller(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/{id}")
    fun getSeller(@PathVariable id: Long): ResponseEntity<SellerResponse> {
        val response = sellerService.getSeller(id)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/store")
    fun createStore(
        @Valid @RequestBody request: StoreCreateRequest
    ): ResponseEntity<StoreResponse> {
        val response = sellerService.createStore(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/{sellerId}/store")
    fun getStore(@PathVariable sellerId: Long): ResponseEntity<StoreResponse> {
        val response = sellerService.getStoreBySellerId(sellerId)
        return ResponseEntity.ok(response)
    }
}
