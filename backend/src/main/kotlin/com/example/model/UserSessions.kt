package com.example.model

import org.jetbrains.exposed.sql.Table

object UserSessions : Table() {
    val sessionId = uuid("id")
    val userId = uuid("user_id")
    val role = enumeration<UserRoles>("role")
    val date = long("date")

    override val primaryKey: PrimaryKey = PrimaryKey(sessionId)
}