package com.example.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

/**
 * Represents a Tangut text to facilitate search operations.
 *
 * @property id Unique identifier for the text. UUID format was chosen to prevent hidden join errors.
 * @property title Title of the text.
 * @property metadata An XML representation containing essential information, such as the text author, etc.
 * @property pageIds A list of IDs for the text lines, including formatted Tangut lines with supplementary information.
 */

@Serializable
data class Text(
    @Contextual val id: UUID = UUID.randomUUID(),
    val pageIds: List<@Contextual UUID>,
    val title: String,
    val metadata: String = "",
)
