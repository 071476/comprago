package com.comprago.gateway

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class GatewayConfig {

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()

    @Value("\${auth-service.url}") lateinit var authUrl: String
    @Value("\${sellers-service.url}") lateinit var sellersUrl: String
    @Value("\${products-service.url}") lateinit var productsUrl: String
    @Value("\${inventory-service.url}") lateinit var inventoryUrl: String
    @Value("\${search-service.url}") lateinit var searchUrl: String
    @Value("\${cart-service.url}") lateinit var cartUrl: String
    @Value("\${orders-service.url}") lateinit var ordersUrl: String
    @Value("\${payments-service.url}") lateinit var paymentsUrl: String
    @Value("\${shipping-service.url}") lateinit var shippingUrl: String
    @Value("\${customers-service.url}") lateinit var customersUrl: String
}
