package com.example.routes

import com.example.service.SearchService
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.searchRoutes(searchService: SearchService) {
    get("/search") {
        val query = call.request.queryParameters["query"] ?: ""
        if (query.isNotEmpty()) {
            call.respond(searchService.search(query))
        } else {
            call.respond(searchService.returnAllSearchResults())
        }
    }
}