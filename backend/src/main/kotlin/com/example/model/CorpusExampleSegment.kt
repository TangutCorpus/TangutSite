package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class CorpusExampleSegment(
    val text: String,
    val highlighted: Boolean
)