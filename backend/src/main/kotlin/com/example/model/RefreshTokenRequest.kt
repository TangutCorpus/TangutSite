package com.example.model

import kotlinx.serialization.Serializable

/**
 * Represents a request to refresh an access token.
 *
 * @property token The refresh token used to generate a new access token.
 */
@Serializable
data class RefreshTokenRequest(val token: String)