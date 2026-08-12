package com.comprago.auth.service

import com.comprago.auth.dto.AuthResponse
import com.comprago.auth.dto.LoginRequest
import com.comprago.auth.dto.RegisterRequest
import com.comprago.auth.model.Role
import com.comprago.auth.model.User
import com.comprago.auth.repository.UserRepository
import com.comprago.auth.security.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {

    fun register(request: RegisterRequest): AuthResponse {

        if (userRepository.existsByEmail(request.email)) {
            throw RuntimeException("El email ya esta registrado")
        }

        val user = User(
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            role = Role.valueOf(request.role)
        )

        userRepository.save(user)

        val token = jwtService.generateToken(user)

        return AuthResponse(
            token = token,
            email = user.getUsername(),
            role = user.role.name,
            message = "Usuario registrado exitosamente"
        )
    }

    fun login(request: LoginRequest): AuthResponse {

        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )

        val user = userRepository.findByEmail(request.email)
            .orElseThrow { RuntimeException("Usuario no encontrado") }

        val token = jwtService.generateToken(user)

        return AuthResponse(
            token = token,
            email = user.getUsername(),
            role = user.role.name,
            message = "Login exitoso"
        )
    }
}
