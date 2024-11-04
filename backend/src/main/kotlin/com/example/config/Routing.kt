package com.example.config

import com.example.routes.searchRoutes
import com.example.routes.textRoutes
import com.example.routes.userRoutes
import com.example.service.SearchService
import com.example.service.TextService
import com.example.service.UserService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(userService: UserService, textService: TextService, searchService: SearchService) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        searchRoutes(searchService)
        userRoutes(userService)
        textRoutes(textService)
    }
}
