package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Fanqie(
    val characters: List<String>,
    val romanizedResult: String,
    val sources: List<TangutDictionarySource>
)