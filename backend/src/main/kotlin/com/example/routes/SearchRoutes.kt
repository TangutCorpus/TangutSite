package com.example.routes;

import com.example.service.SearchService
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.searchRoutes(searchService: SearchService) {
    get("/search") {
        call.respondText(searchService.returnAllSearchResults().toString())
    }

    get("/search/*") {

    }
}