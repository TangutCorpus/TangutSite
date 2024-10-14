package com.example.model

import kotlinx.serialization.Serializable

/**
 * Represents a Tangut text line.
 *
 * @property id
 * @property textId ID of the parent text.
 * @property lineNumber The serial number of the line in a text.
 * @property content Actual XML representation of the fragment.
 * @property comment XML representation containing essential information, such as a gloss or a reconstruction.
 * @property createdAt? The time of the table creation/
 */

@Serializable
data class TextFragment(
    val id: Int?,
    val textId: Int,
    val lineNumber: Int,
    val content: String,
    val comment: String,
    val createdAt: String?,
)