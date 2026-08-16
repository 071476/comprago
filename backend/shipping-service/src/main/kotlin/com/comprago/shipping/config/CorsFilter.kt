package com.comprago.shipping.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class CorsFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*")
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
        response.setHeader("Access-Control-Allow-Headers", "*")
        response.setHeader("Access-Control-Max-Age", "3600")
        if (request.method == "OPTIONS") {
            response.status = HttpServletResponse.SC_OK
            return
        }
        filterChain.doFilter(request, response)
    }
}
