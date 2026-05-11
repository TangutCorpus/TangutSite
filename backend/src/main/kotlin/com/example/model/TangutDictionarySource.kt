package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class TangutDictionarySource(
    val sourceId: String,
    val sourceName: String
)