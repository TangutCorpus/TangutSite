package com.example.routes

import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.model.*
import com.example.service.SecurityService
import com.example.service.UserService
import com.example.utils.json
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.serialization.json.Json

fun Route.authRoutes(securityService: SecurityService, userService: UserService) {
    post("/auth/login") {
        val text = call.receiveText()
        val loginRequest = json.decodeFromString<LoginRequest>(text)
        val session = securityService.authenticate(loginRequest)
        if (session != null) {
            call.sessions.set(session)
            call.respond(HttpStatusCode.OK, session.userId)
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
        }
    }

    post("/auth/signup") {
        val text = call.receiveText()
        val request = json.decodeFromString<SignupRequest>(text)
        val hashedPassword = BCrypt.withDefaults().hashToString(12, request.password.toCharArray())
        val user = ExposedUser(
            username = request.username,
            email = request.email,
            avatarUrl = request.avatarUrl,
            displayName = request.displayName.ifBlank { request.username },
            biography = request.biography,
            role = UserRoles.EDITOR,
        ).toUser(hashedPassword)

        userService.createUser(user)
        val session = securityService.createSession(user)
        call.sessions.set(session)
        call.respond(HttpStatusCode.Created, session.userId)
    }

    post("/auth/logout") {
        val session = call.sessions.get<UserSession>() ?: return@post call.respond(
            HttpStatusCode.NoContent, "No credentials found"
        )
        securityService.logout(session)
        call.sessions.clear<UserSession>()
        call.respond(HttpStatusCode.OK, "Logged out successfully")
    }

    authenticate("user_session") {
        get("/users/me") {
            val session = call.sessions.get<UserSession>()
            if (session != null) {
                val user = userService.getUserById(session.userId)
                if (user != null) {
                    call.respond(user)
                } else {
                    call.respond(HttpStatusCode.NotFound, "User not found")
                }
            } else {
                call.respond(HttpStatusCode.Unauthorized, "User not authenticated")
            }
        }
    }
}
