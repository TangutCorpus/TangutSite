package com.example.web.routes

import com.example.domain.service.UserServiceImpl
import com.example.infrastructure.config.initDatabase
import com.example.infrastructure.database.UserRepositoryImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        var database = initDatabase()
        var userRepository = UserRepositoryImpl(database)
        var userService = UserServiceImpl(userRepository)
        userRoutes(userService)
    }
}
