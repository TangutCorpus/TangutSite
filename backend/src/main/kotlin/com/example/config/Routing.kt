package com.example.config

import com.example.routes.textFragmentRoutes
import com.example.routes.textRoutes
import com.example.routes.userRoutes
import com.example.service.TextFragmentService
import com.example.service.TextService
import com.example.service.UserService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(userService: UserService, textService: TextService, textFragmentService: TextFragmentService) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        userRoutes(userService)
        textRoutes(textService)
        textFragmentRoutes(textFragmentService)
    }
}
