package com.example.config

import com.example.service.SecurityService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respondText

fun Application.configureSecurity(securityService: SecurityService) {
    authentication {
        jwt {
            realm = securityService.realm
            verifier(securityService.getDefaultJWTVerifier())
            validate { securityService::getValidator }
            challenge { defaultScheme, realm ->
                call.respondText(
                    text = "Token is not valid or has expired",
                    status = HttpStatusCode.Unauthorized)
            }
        }
    }
}