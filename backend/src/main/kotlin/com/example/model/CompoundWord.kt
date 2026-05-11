package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class CompoundWord(
    val id: String,
    val characters: String,
    val translation: String,
    val searchCharPosition: Int,
    val secondCharStrokes: Int? = null
)