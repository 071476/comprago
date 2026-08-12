package com.comprago.search.controller

import com.comprago.search.dto.IndexProductRequest
import com.comprago.search.dto.SearchResultResponse
import com.comprago.search.service.SearchService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/search")
class SearchController(private val searchService: SearchService) {

    @PostMapping("/index")
    fun indexProduct(@RequestBody request: IndexProductRequest): ResponseEntity<SearchResultResponse> {
        return ResponseEntity.ok(searchService.indexProduct(request))
    }

    @GetMapping
    fun search(@RequestParam query: String): ResponseEntity<List<SearchResultResponse>> {
        return ResponseEntity.ok(searchService.search(query))
    }

    @GetMapping("/category/{category}")
    fun searchByCategory(@PathVariable category: String): ResponseEntity<List<SearchResultResponse>> {
        return ResponseEntity.ok(searchService.searchByCategory(category))
    }

    @GetMapping("/price")
    fun searchByPrice(@RequestParam min: Double, @RequestParam max: Double): ResponseEntity<List<SearchResultResponse>> {
        return ResponseEntity.ok(searchService.searchByPriceRange(min, max))
    }

    @GetMapping("/instock")
    fun getInStock(): ResponseEntity<List<SearchResultResponse>> {
        return ResponseEntity.ok(searchService.getInStock())
    }

    @GetMapping("/all")
    fun getAll(): ResponseEntity<List<SearchResultResponse>> {
        return ResponseEntity.ok(searchService.getAll())
    }
}
