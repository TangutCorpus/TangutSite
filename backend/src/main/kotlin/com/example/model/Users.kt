package com.example.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.charLength
import org.jetbrains.exposed.sql.javatime.datetime

object Users : Table() {
    val id = uuid("id")
    val nickname = text("nickname").check { it.charLength() less 255 }
    val email = text("email").check { it.charLength() less 255 }
    val password = text("password").check { it.charLength() less 255 }
    val name = text("name").check { it.charLength() less 255 }
    val biography = text("biography")
    val role = text("role")
    val createdAt = datetime("createdAt").nullable()
    override val primaryKey = PrimaryKey(id)
}