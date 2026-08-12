package com.comprago.products.controller

import com.comprago.products.dto.CreateProductRequest
import com.comprago.products.dto.ProductResponse
import com.comprago.products.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    @PostMapping
    fun createProduct(
        @RequestHeader("X-Seller-Id") sellerId: Long,
        @RequestBody request: CreateProductRequest
    ): ResponseEntity<ProductResponse> {
        return ResponseEntity.ok(productService.createProduct(sellerId, request))
    }

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: Long): ResponseEntity<ProductResponse> {
        return ResponseEntity.ok(productService.getProduct(id))
    }

    @GetMapping
    fun getAllProducts(): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok(productService.getAllProducts())
    }

    @GetMapping("/seller/{sellerId}")
    fun getProductsBySeller(@PathVariable sellerId: Long): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok(productService.getProductsBySeller(sellerId))
    }

    @GetMapping("/store/{storeId}")
    fun getProductsByStore(@PathVariable storeId: Long): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok(productService.getProductsByStore(storeId))
    }

    @GetMapping("/category/{category}")
    fun getProductsByCategory(@PathVariable category: String): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok(productService.getProductsByCategory(category))
    }

    @GetMapping("/search")
    fun searchProducts(@RequestParam query: String): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok(productService.searchProducts(query))
    }
}
