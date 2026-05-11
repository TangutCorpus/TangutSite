package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class RelatedWord(
    val language: String,
    val form: String,
    val meaning: String? = null
)