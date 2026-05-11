package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class TangutInitial(
    val character: String,
    val romanization: String,
    val sources: List<TangutDictionarySource>
)