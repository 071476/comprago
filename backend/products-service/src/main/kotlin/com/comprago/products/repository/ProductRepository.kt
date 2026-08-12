package com.comprago.products.repository

import com.comprago.products.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    fun findBySellerId(sellerId: Long): List<Product>
    fun findByStoreId(storeId: Long): List<Product>
    fun findByCategory(category: String): List<Product>
    fun findByNameContainingIgnoreCase(name: String): List<Product>
}
