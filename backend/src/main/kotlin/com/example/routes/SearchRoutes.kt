package com.example.routes

import com.example.service.SearchService
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.searchRoutes(searchService: SearchService) {
    get("/search") {
        val query = call.request.queryParameters["query"]?.trim() ?: ""
        val mode = call.request.queryParameters["mode"] ?: "texts"

        when (mode) {
            "texts" -> {
                val results = if (query.isEmpty()) {
                    searchService.returnAllTextPageSearchResults()
                } else {
                    searchService.searchTextPages(query)
                }
                call.respond(results)
            }

            "dict" -> {
                val results = if (query.isEmpty()) {
                    searchService.getAllDictionaryResults()
                } else {
                    searchService.searchDictionary(query)
                }
                call.respond(results)
            }

            else -> call.respond(HttpStatusCode.BadRequest, "Unknown mode: $mode")
        }
    }
}