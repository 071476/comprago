package com.comprago.cart.service

import com.comprago.cart.dto.*
import com.comprago.cart.model.Cart
import com.comprago.cart.model.CartItem
import com.comprago.cart.repository.CartItemRepository
import com.comprago.cart.repository.CartRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val cartItemRepository: CartItemRepository
) {

    fun getCart(buyerId: Long): CartResponse {
        val cart = cartRepository.findByBuyerId(buyerId)
            ?: Cart(buyerId = buyerId).let { cartRepository.save(it) }
        return cart.toResponse("Carrito obtenido")
    }

    @Transactional
    fun addToCart(buyerId: Long, request: AddToCartRequest): CartResponse {
        val cart = cartRepository.findByBuyerId(buyerId)
            ?: Cart(buyerId = buyerId).let { cartRepository.save(it) }

        val existingItem = cartItemRepository.findByCartIdAndProductId(cart.id, request.productId)

        if (existingItem != null) {
            existingItem.quantity += request.quantity
            existingItem.updatedAt = LocalDateTime.now()
            cartItemRepository.save(existingItem)
        } else {
            val item = CartItem(
                cart = cart,
                productId = request.productId,
                productName = request.productName,
                price = request.price,
                quantity = request.quantity,
                imageUrl = request.imageUrl
            )
            cartItemRepository.save(item)
            cart.items.add(item)
        }

        cart.updatedAt = LocalDateTime.now()
        cartRepository.save(cart)
        return cart.toResponse("Producto agregado al carrito")
    }

    @Transactional
    fun updateQuantity(buyerId: Long, itemId: Long, request: UpdateQuantityRequest): CartResponse {
        val cart = cartRepository.findByBuyerId(buyerId)
            ?: throw NoSuchElementException("Carrito no encontrado")

        val item = cartItemRepository.findById(itemId).orElseThrow {
            NoSuchElementException("Item no encontrado")
        }

        if (request.quantity <= 0) {
            cartItemRepository.delete(item)
            cart.items.remove(item)
        } else {
            item.quantity = request.quantity
            cartItemRepository.save(item)
        }

        cart.updatedAt = LocalDateTime.now()
        cartRepository.save(cart)
        return cart.toResponse("Cantidad actualizada")
    }

    @Transactional
    fun removeItem(buyerId: Long, itemId: Long): CartResponse {
        val cart = cartRepository.findByBuyerId(buyerId)
            ?: throw NoSuchElementException("Carrito no encontrado")

        val item = cartItemRepository.findById(itemId).orElseThrow {
            NoSuchElementException("Item no encontrado")
        }

        cartItemRepository.delete(item)
        cart.items.remove(item)
        cart.updatedAt = LocalDateTime.now()
        cartRepository.save(cart)
        return cart.toResponse("Producto eliminado del carrito")
    }

    @Transactional
    fun clearCart(buyerId: Long): CartResponse {
        val cart = cartRepository.findByBuyerId(buyerId)
            ?: throw NoSuchElementException("Carrito no encontrado")

        cartItemRepository.deleteByCartId(cart.id)
        cart.items.clear()
        cart.updatedAt = LocalDateTime.now()
        cartRepository.save(cart)
        return cart.toResponse("Carrito vaciado")
    }

    private fun Cart.toResponse(message: String) = CartResponse(
        id = this.id,
        buyerId = this.buyerId,
        items = this.items.map { item ->
            CartItemResponse(
                id = item.id,
                productId = item.productId,
                productName = item.productName,
                price = item.price,
                quantity = item.quantity,
                subtotal = item.subtotal,
                imageUrl = item.imageUrl
            )
        },
        total = this.total,
        itemCount = this.items.size,
        message = message
    )
}
