package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class CorpusExample(
    val textId: String,
    val textTitle: String,
    val pageNumber: Int? = null,
    val segments: List<CorpusExampleSegment>
)