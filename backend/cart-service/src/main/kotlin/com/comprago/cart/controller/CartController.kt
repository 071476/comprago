package com.comprago.cart.controller

import com.comprago.cart.dto.*
import com.comprago.cart.service.CartService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cart")
class CartController(private val cartService: CartService) {

    @GetMapping("/{buyerId}")
    fun getCart(@PathVariable buyerId: Long): ResponseEntity<CartResponse> {
        return ResponseEntity.ok(cartService.getCart(buyerId))
    }

    @PostMapping("/{buyerId}/items")
    fun addToCart(
        @PathVariable buyerId: Long,
        @RequestBody request: AddToCartRequest
    ): ResponseEntity<CartResponse> {
        return ResponseEntity.ok(cartService.addToCart(buyerId, request))
    }

    @PutMapping("/{buyerId}/items/{itemId}")
    fun updateQuantity(
        @PathVariable buyerId: Long,
        @PathVariable itemId: Long,
        @RequestBody request: UpdateQuantityRequest
    ): ResponseEntity<CartResponse> {
        return ResponseEntity.ok(cartService.updateQuantity(buyerId, itemId, request))
    }

    @DeleteMapping("/{buyerId}/items/{itemId}")
    fun removeItem(
        @PathVariable buyerId: Long,
        @PathVariable itemId: Long
    ): ResponseEntity<CartResponse> {
        return ResponseEntity.ok(cartService.removeItem(buyerId, itemId))
    }

    @DeleteMapping("/{buyerId}")
    fun clearCart(@PathVariable buyerId: Long): ResponseEntity<CartResponse> {
        return ResponseEntity.ok(cartService.clearCart(buyerId))
    }
}
