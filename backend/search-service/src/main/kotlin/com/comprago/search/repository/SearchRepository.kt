package com.comprago.search.repository

import com.comprago.search.model.SearchDocument
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SearchRepository : JpaRepository<SearchDocument, Long> {
    fun findByProductId(productId: Long): SearchDocument?

    @Query("SELECT s FROM SearchDocument s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(s.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    fun searchByText(query: String): List<SearchDocument>

    fun findByCategory(category: String): List<SearchDocument>

    @Query("SELECT s FROM SearchDocument s WHERE s.price >= :minPrice AND s.price <= :maxPrice")
    fun findByPriceRange(minPrice: Double, maxPrice: Double): List<SearchDocument>

    @Query("SELECT s FROM SearchDocument s WHERE s.inStock = true")
    fun findInStock(): List<SearchDocument>
}
