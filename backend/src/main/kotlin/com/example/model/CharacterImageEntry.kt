package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterImageEntry(
    val imageId: String,
    val url: String,
    val thumbnail: String? = null
)