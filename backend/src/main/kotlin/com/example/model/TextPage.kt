package com.example.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

/**
 * Represents a Tangut text line.
 *
 * @property id Unique identifier for the text fragment. UUID format was chosen to prevent hidden join errors.
 * @property textId ID of the parent text. UUID format was chosen to prevent hidden join errors.
 * @property pageNumber The serial number of the page in a text.
 * @property pureText The raw text without XML tags, utilised for search purposes.
 * @property glossedTextXML Actual XML representation of the fragment.
 * @property translationsXML XML representation containing essential information, such as a gloss or a reconstruction.
 */

@Serializable
data class TextPage(
    @Contextual val id: UUID = UUID.randomUUID(),
    @Contextual val textId: UUID,
    val imagesIDs: List<@Contextual UUID> = emptyList(),
    val pageNumber: Int = 0,
    val pureText: String = "",
    val glossedTextXML: String = "",
    val translationsXML: String = "",
)
