package com.comprago.gateway

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/api")
class GatewayController(
    private val restTemplate: RestTemplate,
    private val config: GatewayConfig
) {

    @RequestMapping("/auth/**")
    fun auth(request: HttpServletRequest, @RequestBody(required = false) body: Any?): ResponseEntity<String> {
        return forward(config.authUrl, request, body)
    }

    @RequestMapping("/sellers/**")
    fun sellers(request: HttpServletRequest, @RequestBody(required = false) body: Any?): ResponseEntity<String> {
        return forward(config.sellersUrl, request, body)
    }

    @RequestMapping("/products/**")
    fun products(request: HttpServletRequest, @RequestBody(required = false) body: Any?): ResponseEntity<String> {
        return forward(config.productsUrl, request, body)
    }

    @RequestMapping("/inventory/**")
    fun inventory(request: HttpServletRequest, @RequestBody(required = false) body: Any?): ResponseEntity<String> {
        return forward(config.inventoryUrl, request, body)
    }

    @RequestMapping("/search/**")
    fun search(request: HttpServletRequest, @RequestBody(required = false) body: Any?): ResponseEntity<String> {
        return forward(config.searchUrl, request, body)
    }

    @RequestMapping("/cart/**")
    fun cart(request: HttpServletRequest, @RequestBody(required = false) body: Any?): ResponseEntity<String> {
        return forward(config.cartUrl, request, body)
    }

    @RequestMapping("/orders/**")
    fun orders(request: HttpServletRequest, @RequestBody(required = false) body: Any?): ResponseEntity<String> {
        return forward(config.ordersUrl, request, body)
    }

    @RequestMapping("/payments/**")
    fun payments(request: HttpServletRequest, @RequestBody(required = false) body: Any?): ResponseEntity<String> {
        return forward(config.paymentsUrl, request, body)
    }

    @RequestMapping("/shipping/**")
    fun shipping(request: HttpServletRequest, @RequestBody(required = false) body: Any?): ResponseEntity<String> {
        return forward(config.shippingUrl, request, body)
    }

    @RequestMapping("/customers/**")
    fun customers(request: HttpServletRequest, @RequestBody(required = false) body: Any?): ResponseEntity<String> {
        return forward(config.customersUrl, request, body)
    }

    @GetMapping("/health")
    fun health(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(mapOf(
            "status" to "UP",
            "service" to "api-gateway",
            "version" to "1.0"
        ))
    }

    private fun forward(baseUrl: String, request: HttpServletRequest, body: Any?): ResponseEntity<String> {
        val path = request.requestURI.removePrefix("/api")
        val queryString = request.queryString
        val targetUrl = "$baseUrl/api$path${if (queryString != null) "?$queryString" else ""}"

        val headers = HttpHeaders()
        request.headerNames.asIterator().forEach { name ->
            if (name.lowercase() !in listOf("host", "content-length")) {
                headers.set(name, request.getHeader(name))
            }
        }

        val method = when (request.method.uppercase()) {
            "GET" -> HttpMethod.GET
            "POST" -> HttpMethod.POST
            "PUT" -> HttpMethod.PUT
            "PATCH" -> HttpMethod.PATCH
            "DELETE" -> HttpMethod.DELETE
            else -> HttpMethod.GET
        }

        val entity = HttpEntity(body, headers)

        return try {
            val response = restTemplate.exchange(targetUrl, method, entity, String::class.java)
            ResponseEntity.status(response.statusCode).body(response.body)
        } catch (e: Exception) {
            ResponseEntity.status(503).body("""{"error":"Servicio no disponible","detail":"${e.message}"}""")
        }
    }
}
