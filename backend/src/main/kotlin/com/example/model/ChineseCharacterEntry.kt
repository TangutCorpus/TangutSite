package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class ChineseCharacterEntry(
    val character: String,
    val textId: String? = null,
    val textTitle: String? = null
)