package com.example.routes

import com.example.service.TextService
import com.example.service.UserService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(userService: UserService, textService: TextService) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        userRoutes(userService)
        textRoutes(textService)
    }
}
