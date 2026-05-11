package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class DictionaryReference(
    val dictionaryId: String,
    val dictionaryName: String,
    val pageOrNumber: String
)