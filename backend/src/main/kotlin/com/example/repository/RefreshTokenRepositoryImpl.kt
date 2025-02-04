package com.example.repository

import com.example.model.RefreshToken
import com.example.model.RefreshTokens
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class RefreshTokenRepositoryImpl(private val db: Database) : RefreshTokenRepository {
    override fun save(refreshToken: RefreshToken) = transaction(db) {
        RefreshTokens.insert {
            it[id] = refreshToken.id
            it[token] = refreshToken.token
        } get RefreshTokens.id
    }

    override fun findTokenById(id: UUID): String? = transaction(db) {
        RefreshTokens.selectAll().where { RefreshTokens.id eq id }.map { RefreshTokens.token.toString() }.singleOrNull()
    }

    override fun findIdByToken(token: String): UUID? = transaction(db) {
        RefreshTokens.selectAll()
            .where { RefreshTokens.token eq token }
            .map { it[RefreshTokens.id] }
            .singleOrNull()
    }

    override fun getAll(): List<RefreshToken> = transaction(db) {
        RefreshTokens.selectAll().map { it.toRefreshToken() }
    }
}

private fun ResultRow.toRefreshToken(): RefreshToken {
    return RefreshToken(
        id = this[RefreshTokens.id],
        token = this[RefreshTokens.token],
    )
}