package com.example.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class TextRequest(
    val pageIds: List<@Contextual UUID>,
    val title: String,
    val metadata: String = "",
)
