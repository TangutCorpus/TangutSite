package com.example.routes

import com.example.service.UserService
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put

fun Route.userRoutes(userService: UserService) {
    post("/users") {
        TODO("Not yet implemented")
    }

    get("/users/{id}") {
        TODO("Not yet implemented")
    }

    put("/users/{id}") {
        TODO("Not yet implemented")
    }

    delete("/users/{id}") {
        TODO("Not yet implemented")
    }
}
