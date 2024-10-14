package com.example.model

import kotlinx.serialization.Serializable

/**
 * Represents a Tangut text to facilitate search operations.
 *
 * @property id
 * @property comment An XML representation containing essential information, such as the title ot the text author.
 * @property lineIds A list of IDs for the text lines, including formatted Tangut lines with supplementary information.
 * @property pureText The raw text without XML tags, utilised for search purposes.
 * @property createdAt? The time of the table creation/
 */

@Serializable
data class Text(
    val id: Int?,
    val comment: String,
    val lineIds: List<Int>,
    val pureText: String,
    val createdAt: String?
)
