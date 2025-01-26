package com.example.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID


/**
 * Represents a User.
 *
 * @property id Unique identifier for the user.
 * @property email Email address of the user, used for login and communication.
 * @property password Encrypted password for authentication purposes.
 * @property nickname Shorthand used for tagging and as a user page address.
 * @property name Full name, which can be in any supported language, useful in academic and professional contexts.
 * @property biography Descriptive text outlining the user's achievements and providing links to external resources.
 * @property role The role of the registered user, which determines access and permissions. Possible values include "Editor", "Moderator", and "Administrator".
 */

@Serializable
data class User(
    @Contextual val id: UUID = UUID.randomUUID(),
    val nickname: String,
    val email: String,
    val password: String,
    val name: String,
    val biography: String,
    val role: String
)