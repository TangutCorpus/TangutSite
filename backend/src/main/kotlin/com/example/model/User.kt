package com.example.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

/**
 * Represents an User.
 *
 * @property id Unique identifier for the user. UUID format was chosen to prevent hidden join errors.
 * @property email Email address of the user, used for login and communication.
 * @property password Encrypted password for authentication purposes.
 * @property username Shorthand used for tagging and as a user page address.
 * @property avatarUrl URL of the profile picture.
 * @property displayName Full name, which can be in any supported language, useful in academic and professional contexts.
 * @property biography Descriptive text outlining the user's achievements and providing links to external resources.
 * @property role The role of the registered user, which determines access and permissions. Possible values include "Editor", "Moderator", and "Administrator".
 */

@Serializable
data class User(
    @Contextual val id: UUID = UUID.randomUUID(),
    val username: String,
    val email: String,
    val password: String,
    val avatarUrl: String = "",
    val displayName: String = username,
    val biography: String = "",
    val role: UserRoles = UserRoles.EDITOR,
)

fun User.updateFromExposedUser(exposedUser: ExposedUser): User {
    return this.copy(
        username = exposedUser.username,
        email = exposedUser.email,
        avatarUrl = exposedUser.avatarUrl,
        displayName = exposedUser.displayName,
        biography = exposedUser.biography,
        role = exposedUser.role,
    )
}