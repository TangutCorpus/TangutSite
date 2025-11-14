package com.example.config

import com.example.model.UserSession
import com.example.service.SecurityService
import com.example.utils.json
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import io.ktor.server.sessions.serialization.*

fun Application.configureSecurity(isProduction: Boolean, securityService: SecurityService, maxAge: Long) {
    install(Sessions) {
        cookie<UserSession>("user_session") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = maxAge
            cookie.httpOnly = isProduction
            cookie.secure = isProduction
            cookie.extensions["SameSite"] = "lax"
            serializer = KotlinxSessionSerializer(json)
        }
    }
    install(Authentication) {
        session<UserSession>("user_session") {
            validate { session ->
                securityService.getValidator(session, maxAge)
            }
            challenge {
                call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
            }
        }
    }
}