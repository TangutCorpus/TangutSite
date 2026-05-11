package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class SanskritSyllable(
    val syllable: String,
    val language: String
)