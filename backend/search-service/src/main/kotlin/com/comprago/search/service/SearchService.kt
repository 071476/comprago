package com.comprago.search.service

import com.comprago.search.dto.IndexProductRequest
import com.comprago.search.dto.SearchResultResponse
import com.comprago.search.model.SearchDocument
import com.comprago.search.repository.SearchRepository
import org.springframework.stereotype.Service

@Service
class SearchService(private val repository: SearchRepository) {

    fun indexProduct(request: IndexProductRequest): SearchResultResponse {
        val document = repository.findByProductId(request.productId)?.apply {
            name = request.name
            description = request.description
            price = request.price
            category = request.category
            sellerId = request.sellerId
            storeId = request.storeId
            storeName = request.storeName
            imageUrl = request.imageUrl
            inStock = request.inStock
        } ?: SearchDocument(
            productId = request.productId,
            name = request.name,
            description = request.description,
            price = request.price,
            category = request.category,
            sellerId = request.sellerId,
            storeId = request.storeId,
            storeName = request.storeName,
            imageUrl = request.imageUrl,
            inStock = request.inStock
        )
        val saved = repository.save(document)
        return saved.toResponse()
    }

    fun search(query: String): List<SearchResultResponse> {
        return repository.searchByText(query).map { it.toResponse() }
    }

    fun searchByCategory(category: String): List<SearchResultResponse> {
        return repository.findByCategory(category).map { it.toResponse() }
    }

    fun searchByPriceRange(minPrice: Double, maxPrice: Double): List<SearchResultResponse> {
        return repository.findByPriceRange(minPrice, maxPrice).map { it.toResponse() }
    }

    fun getInStock(): List<SearchResultResponse> {
        return repository.findInStock().map { it.toResponse() }
    }

    fun getAll(): List<SearchResultResponse> {
        return repository.findAll().map { it.toResponse() }
    }

    private fun SearchDocument.toResponse() = SearchResultResponse(
        id = this.id,
        productId = this.productId,
        name = this.name,
        description = this.description,
        price = this.price,
        category = this.category,
        storeName = this.storeName,
        imageUrl = this.imageUrl,
        inStock = this.inStock
    )
}
