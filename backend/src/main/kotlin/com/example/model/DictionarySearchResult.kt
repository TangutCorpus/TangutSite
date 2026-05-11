package com.example.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class DictionarySearchResult(
    @Contextual val id: UUID,
    val character: String,
    val translation: String,
    val strokeCount: Int,
    val searchCharPosition: Int
)