package com.example.service

import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.model.LoginRequest
import com.example.model.User
import com.example.model.UserSession
import com.example.repository.SessionRepository
import com.example.repository.UserRepository
import java.time.Instant
import java.util.*

class SecurityService(private val userRepository: UserRepository, private val sessionRepository: SessionRepository) {
    fun getValidator(session: UserSession, maxAge: Long): UserSession? {
        val currentSession = sessionRepository.getSessionById(session.sessionId)
        val now = Instant.now().toEpochMilli()
        return if (currentSession == null || currentSession.date + (maxAge * 1000) < now) {
            null
        } else {
            currentSession
        }
    }

    fun createSession(user: User): UserSession {
        val session = UserSession(UUID.randomUUID(), user.id, user.role)
        sessionRepository.addSession(session)
        return session
    }

    fun authenticate(loginRequest: LoginRequest): UserSession? {
        val user = userRepository.getUserByEmail(loginRequest.email) ?: return null

        val verified = BCrypt.verifyer().verify(loginRequest.password.toCharArray(), user.password)
        if (!verified.verified) return null

        val session = UserSession(UUID.randomUUID(), user.id, user.role)
        sessionRepository.addSession(session)
        return session
    }

    fun logout(session: UserSession) {
        sessionRepository.deleteSessionById(session.sessionId)
    }
}