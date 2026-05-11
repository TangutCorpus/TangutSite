package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class TibetanSyllable(
    val syllable: String,
    val textId: String? = null
)