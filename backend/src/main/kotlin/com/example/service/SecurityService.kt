package com.example.service

import com.auth0.jwt.JWTVerifier
import com.example.model.AuthResponse
import com.example.model.RefreshTokenRequest
import com.example.model.RefreshTokenResponse
import com.example.model.UserLoginRequest
import com.example.model.UserRoles
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import java.util.UUID

interface SecurityService {
    suspend fun getValidator(credential: JWTCredential): JWTPrincipal?
    fun createAccessToken(userEmail: String, role: UserRoles): String
    fun createRefreshToken(userEmail: String, role: UserRoles): String
    fun getDefaultJWTVerifier(): JWTVerifier
    suspend fun authenticate(loginRequest: UserLoginRequest): AuthResponse?
    suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse?
    fun saveRefreshToken(id: UUID, token: String)
    val realm: String
}