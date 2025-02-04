package com.example.model

import kotlinx.serialization.Serializable

/**
 * Represents the response to a refresh token request.
 *
 * This class provides a newly issued access token after a successful refresh request.
 *
 * @property token The newly issued access token.
 */
@Serializable
data class RefreshTokenResponse(val token: String)