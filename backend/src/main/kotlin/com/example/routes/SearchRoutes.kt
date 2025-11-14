package com.example.routes

import com.example.service.SearchService
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.searchRoutes(searchService: SearchService) {
    get("/search") {
        val query = call.request.queryParameters["query"] ?: ""
        val mode = call.request.queryParameters["mode"] ?: ""
        if (query.isNotEmpty()) {
            when (mode) {
                "texts" -> call.respond(searchService.searchTextPages(query))
                "dict" -> call.respond(HttpStatusCode.ServiceUnavailable, "Unavailable: {$mode}")
                else -> call.respond(HttpStatusCode.BadRequest, "Invalid mode: {$mode}")
            }
        } else {
            when (mode) {
                "texts" -> call.respond(searchService.returnAllTextPageSearchResults())
                "dict" ->  call.respond(HttpStatusCode.ServiceUnavailable, "Unavailable: {$mode}")
                else -> call.respond(HttpStatusCode.BadRequest, "Invalid mode: {$mode}")
            }
        }
    }
}