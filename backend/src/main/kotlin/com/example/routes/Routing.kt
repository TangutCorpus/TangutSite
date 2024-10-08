package com.example.routes

import com.example.config.initDatabase
import com.example.repository.UserRepositoryImpl
import com.example.service.UserServiceImpl
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
