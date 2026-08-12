package com.comprago.products.service

import com.comprago.products.dto.CreateProductRequest
import com.comprago.products.dto.ProductResponse
import com.comprago.products.model.Product
import com.comprago.products.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(private val repository: ProductRepository) {

    fun createProduct(sellerId: Long, request: CreateProductRequest): ProductResponse {
        val product = Product(
            name = request.name,
            description = request.description,
            price = request.price,
            category = request.category,
            imageUrls = request.imageUrls.toMutableList(),
            sellerId = sellerId,
            storeId = request.storeId
        )
        val saved = repository.save(product)
        return saved.toResponse("Producto creado exitosamente")
    }

    fun getProduct(id: Long): ProductResponse {
        val product = repository.findById(id).orElseThrow {
            NoSuchElementException("Producto no encontrado")
        }
        return product.toResponse("Producto encontrado")
    }

    fun getProductsBySeller(sellerId: Long): List<ProductResponse> {
        return repository.findBySellerId(sellerId).map {
            it.toResponse("")
        }
    }

    fun getProductsByStore(storeId: Long): List<ProductResponse> {
        return repository.findByStoreId(storeId).map {
            it.toResponse("")
        }
    }

    fun getProductsByCategory(category: String): List<ProductResponse> {
        return repository.findByCategory(category).map {
            it.toResponse("")
        }
    }

    fun searchProducts(query: String): List<ProductResponse> {
        return repository.findByNameContainingIgnoreCase(query).map {
            it.toResponse("")
        }
    }

    fun getAllProducts(): List<ProductResponse> {
        return repository.findAll().map {
            it.toResponse("")
        }
    }

    private fun Product.toResponse(message: String) = ProductResponse(
        id = this.id,
        name = this.name,
        description = this.description,
        price = this.price,
        category = this.category,
        imageUrls = this.imageUrls,
        sellerId = this.sellerId,
        storeId = this.storeId,
        active = this.active,
        message = message
    )
}
