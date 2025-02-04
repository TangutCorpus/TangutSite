package com.example.model

import org.jetbrains.exposed.sql.Table

object RefreshTokens : Table() {
    val id = uuid("id")
    val token = text("token")
    override val primaryKey = PrimaryKey(id)
}