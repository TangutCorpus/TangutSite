package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterComponent(
    val id: String,
    val character: String,
    val role: ComponentRole,
    val articleId: String
)