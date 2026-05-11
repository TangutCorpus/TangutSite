package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class TangutRhyme(
    val number: String,
    val chapter: String? = null,
    val sources: List<TangutDictionarySource>
)