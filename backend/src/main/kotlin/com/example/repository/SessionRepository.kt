package com.example.repository

import com.example.model.UserSession
import com.example.model.UserSessions
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.util.*

class SessionRepository(private val db: Database) {
    init {
        transaction {
            SchemaUtils.create(UserSessions)
        }
    }

    fun addSession(session: UserSession) = transaction(db) {
        UserSessions.insert {
            it[sessionId] = session.sessionId
            it[userId] = session.userId
            it[role] = session.role
            it[date] = Instant.now().toEpochMilli()
        } get UserSessions.sessionId
    }

    fun updateSession(session: UserSession) = transaction(db) {
        UserSessions.update({ UserSessions.sessionId eq session.sessionId }) {
            it[userId] = session.userId
            it[role] = session.role
            it[date] = Instant.now().toEpochMilli()
        }
    }

    fun deleteSessionById(id: UUID) = transaction(db) {
        UserSessions.deleteWhere { UserSessions.sessionId eq id }
    }

    fun getSessionById(id: UUID) = transaction(db) {
        UserSessions.selectAll().where { UserSessions.sessionId eq id }.mapNotNull { it.toUserSession() }.singleOrNull()
    }
}

private fun ResultRow.toUserSession(): UserSession {
    return UserSession(
        sessionId = this[UserSessions.sessionId],
        userId = this[UserSessions.userId],
        role = this[UserSessions.role],
        date = this[UserSessions.date],
    )
}