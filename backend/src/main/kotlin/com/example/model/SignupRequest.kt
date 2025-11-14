package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val email: String,
    val username: String,
    val password: String,
    val avatarUrl: String = "",
    val displayName: String = "",
    val biography: String = "",
)