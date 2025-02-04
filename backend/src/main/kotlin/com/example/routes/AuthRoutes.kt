package com.example.routes

import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.model.AuthResponse
import com.example.model.ExposedUser
import com.example.model.RefreshTokenRequest
import com.example.model.UserLoginRequest
import com.example.model.toUser
import com.example.service.SecurityService
import com.example.service.UserService
import com.example.utils.toUUIDOrNull
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Route.authRoutes(securityService: SecurityService, userService: UserService) {
    post("/auth/login") {
        val loginRequest = call.receive<UserLoginRequest>()
        val user = userService.getUserByEmail(loginRequest.email)

        if (user != null && BCrypt.verifyer()
                .verify(loginRequest.password.toCharArray(), user.password.toCharArray()).verified
        ) {
            val accessToken = securityService.createAccessToken(user.email, user.role)
            val refreshToken = securityService.createRefreshToken(user.email, user.role)
            securityService.saveRefreshToken(user.id, refreshToken)

            call.respond(AuthResponse(accessToken, refreshToken))
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
        }
    }

    post("/auth/signup") {
        val exposedUser = call.receive<ExposedUser>()
        val password = call.receiveParameters()["password"] ?: return@post call.respond(
            HttpStatusCode.BadRequest, "Password required"
        )

        if (!isValidPassword(password)) {
            return@post call.respond(HttpStatusCode.BadRequest, "Weak password")
        }

        val hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray())
        val user = exposedUser.toUser(hashedPassword)

        userService.createUser(user)
        val token = securityService.createAccessToken(user.email, user.role)
        call.respond(mapOf("token" to token))
    }

    post("/auth/refresh") {
        val request = call.receive<RefreshTokenRequest>()
        val newToken = securityService.refreshToken(request)

        if (newToken != null) {
            call.respond(HttpStatusCode.OK, newToken)
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid or expired refresh token")
        }
    }

    get("/users/me") {
        val userId = call.principal<UserIdPrincipal>()?.name
        if (userId != null) {
            val user = userService.getUserById(userId.toUUIDOrNull())
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


/**
 * It's a temporary function which is to be replaced.
 */
private fun isValidPassword(password: String): Boolean {
    return password.length >= 8 && password.any { it.isDigit() } && password.any { it.isUpperCase() }
}
