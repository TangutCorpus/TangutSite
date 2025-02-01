package com.example.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

/**
 * Represents a Tangut text line.
 *
 * @property id Unique identifier for the text fragment. UUID format was chosen to prevent hidden join errors.
 * @property textId ID of the parent text. UUID format was chosen to prevent hidden join errors.
 * @property lineNumber The serial number of the line in a text.
 * @property contentXML Actual XML representation of the fragment.
 * @property commentXML XML representation containing essential information, such as a gloss or a reconstruction.
 * @property createdAt? The time of the table creation.
 */

@Serializable
data class TextFragment(
    @Contextual val id: UUID = UUID.randomUUID(),
    @Contextual val textId: UUID,
    val lineNumber: Int = 0,
    val contentXML: String = "",
    val commentXML: String = "",
    val createdAt: LocalDate?,
)
