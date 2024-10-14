package com.example.routes

import com.example.service.TextService
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put

fun Route.textRoutes(textService: TextService) {
    post("/texts") {
        TODO("Not yet implemented")
    }

    get("/texts/{id}") {
        TODO("Not yet implemented")
    }

    put("/texts/{id}") {
        TODO("Not yet implemented")
    }

    delete("/texts/{id}") {
        TODO("Not yet implemented")
    }
}