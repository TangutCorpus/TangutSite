package com.example.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class TextPageRequest(
    @Contextual val textId: UUID,
    val imagesIDs: List<@Contextual UUID> = emptyList(),
    val pageNumber: Int = 0,
    val pureText: String = "",
    val glossedTextXML: String = "",
    val translationsXML: String = "",
)
