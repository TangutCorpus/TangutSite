package com.example.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.charLength

object Users : Table() {
    val id = uuid("id")
    val username = text("username").check { it.charLength() less 255 }.uniqueIndex()
    val email = text("email").uniqueIndex()
    val password = text("password")
    val avatarUrl = text("avatarUrl")
    val displayName = text("displayName").check { it.charLength() less 255 }
    val biography = text("biography")
    val role = enumeration<UserRoles>("role")
    override val primaryKey = PrimaryKey(id)
}