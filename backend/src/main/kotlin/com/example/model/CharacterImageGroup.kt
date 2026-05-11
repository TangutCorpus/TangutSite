package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterImageGroup(
    val textId: String,
    val textName: String,
    val previewImages: List<CharacterImageEntry>,
    val collapsedImages: List<CharacterImageEntry>
)