package com.example.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

/**
 * Represents a stored refresh token.
 *
 * @property id Unique identifier for the refresh token.
 * @property token The refresh token string used for renewing access.
 */
@Serializable
data class RefreshToken(
    @Contextual val id: UUID,
    val token: String
)