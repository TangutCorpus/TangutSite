package com.example.config

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.respondText


fun Application.configureExceptionHandling() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            if (call is IllegalArgumentException) {
                call.respondText(cause.message ?: "Unknown error", status = HttpStatusCode.BadRequest)
            } else {
                call.respondText(cause.message ?: "Unknown error", status = HttpStatusCode.InternalServerError)
            }
        }
    }
}