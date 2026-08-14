package com.comprago.products.config

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration
class CorsConfig {
    @Bean
    fun corsFilterRegistration(): FilterRegistrationBean<Filter> {
        val registration = FilterRegistrationBean<Filter>()
        registration.filter = Filter { request: ServletRequest, response: ServletResponse, chain: FilterChain ->
            val res = response as HttpServletResponse
            val req = request as HttpServletRequest
            res.setHeader("Access-Control-Allow-Origin", "*")
            res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
            res.setHeader("Access-Control-Allow-Headers", "*")
            res.setHeader("Access-Control-Max-Age", "3600")
            if (req.method == "OPTIONS") {
                res.status = 200
                return@Filter
            }
            chain.doFilter(request, response)
        }
        registration.addUrlPatterns("/*")
        registration.order = Ordered.HIGHEST_PRECEDENCE
        return registration
    }
}
