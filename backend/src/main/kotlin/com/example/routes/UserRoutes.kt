package com.example.routes

import com.example.service.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put

fun Route.userRoutes(userService: UserService) {
    post("/users") {
        call.respondText("Not yet implemented", status = HttpStatusCode.NotImplemented)
    }

    get("/users/{id}") {
        call.respondText("Not yet implemented", status = HttpStatusCode.NotImplemented)
    }

    put("/users/{id}") {
        call.respondText("Not yet implemented", status = HttpStatusCode.NotImplemented)
    }

    delete("/users/{id}") {
        call.respondText("Not yet implemented", status = HttpStatusCode.NotImplemented)
    }
}
