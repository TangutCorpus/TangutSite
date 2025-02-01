package com.example.model

import kotlinx.serialization.Serializable

/**
 * Represents a minimal response containing user details.
 *
 * Used in API responses to provide basic user data for account creation and further updating.
 *
 * @property email Email address of the user, used for login and communication.
 * @property username Shorthand used for tagging and as a user page address.
 * @property avatarUrl URL of the profile picture.
 * @property displayName Full name, which can be in any supported language, useful in academic and professional contexts.
 * @property biography Descriptive text outlining the user's achievements and providing links to external resources.
 */

@Serializable
data class ExposedUser(
    val username: String,
    val email: String,
    val avatarUrl: String = "",
    val displayName: String = "",
    val biography: String = "",
)

/**
 * Converts an `ExposedUser` to a `User` model.
 *
 * @param password User's password. It's passes separately for the security's sake.
 */
fun ExposedUser.toUser(password: String) = User(
    username = this.username,
    email = this.email,
    password = password,
    avatarUrl = this.avatarUrl,
    displayName = if (this.displayName.isNotBlank()) this.displayName else this.username, // Compute if empty
    biography = this.biography,
)