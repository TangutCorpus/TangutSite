package com.example.model

import kotlinx.serialization.Serializable

/**
 * Represents a user login request.
 *
 * This request is sent by users attempting to authenticate.
 * If successful, an `AuthResponse` containing tokens is returned.
 *
 * @property email The email provided for authentication.
 * @property password The password associated with the account.
 */
@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
)