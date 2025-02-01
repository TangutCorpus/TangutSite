package com.example.model

/**
 * Defines user roles, determining access levels and permissions.
 *
 * - `EDITOR`: Can create and edit content.
 * - `MODERATOR`: Can manage user-generated content.
 * - `ADMIN`: Has full control over the system, including user management and configuration.
 */

enum class UserRoles {
    EDITOR,
    MODERATOR,
    ADMIN,
}