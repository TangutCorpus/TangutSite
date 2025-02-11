package com.example.repository

import com.example.model.RefreshToken
import java.util.UUID

interface RefreshTokenRepository {
    fun save(refreshToken: RefreshToken): UUID
    fun findTokenById(id: UUID): String?
    fun findIdByToken(token: String): UUID?
    fun getAll(): List<RefreshToken>
    fun deleteTokenById(id: UUID)
}