package com.example.model

import kotlinx.serialization.Serializable

/**
 * Represents the authentication response containing tokens.
 *
 * This class is used to return authentication tokens after a successful login.
 *
 * @property accessToken A short-lived JWT access token for authentication.
 * @property refreshToken A long-lived token used to refresh the access token.
 */
@Serializable
data class AuthResponse(
    val accessToken: String,
    val refreshToken: String
)