package com.example.routes

import com.example.model.TextPage
import com.example.service.TextPageService
import com.example.utils.toUUIDOrNull
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put

fun Route.textPageRoutes(textPageService: TextPageService) {
    post("/pages") {
        val page = call.receive<TextPage>()
        textPageService.addTextPage(page)
        call.respondText("Page added successfully", status = HttpStatusCode.Created)
    }

    get("/pages") {
        val pages = textPageService.getAllTextPages()
        call.respond(pages)
    }

    get("/pages/{id}") {
        val id = call.parameters["id"]?.toUUIDOrNull()
        val page = textPageService.getTextPageById(id)
        if (page != null) {
            call.respond(page)
        } else {
            call.respondText("Page not found", status = HttpStatusCode.NotFound)
        }
    }

    put("/pages/{id}") {
        val id = call.parameters["id"]?.toUUIDOrNull() ?: return@put
        val updatedPage = call.receive<TextPage>()
        if (textPageService.updateTextPage(updatedPage.copy(id = id))) {
            call.respondText("Page updated successfully", status = HttpStatusCode.OK)
        } else {
            call.respondText("Page not modified", status = HttpStatusCode.NotModified)
        }

    }

    delete("/pages/{id}") {
        val id = call.parameters["id"]?.toUUIDOrNull()
        if (textPageService.deleteTextPageById(id)) {
            call.respondText("Page deleted successfully", status = HttpStatusCode.OK)
        } else {
            call.respondText("Page not deleted", status = HttpStatusCode.NotModified)
        }
    }
}
