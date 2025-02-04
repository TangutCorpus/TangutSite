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
    post("/fragments") {
        val fragment = call.receive<TextPage>()
        textPageService.addTextPage(fragment)
        call.respondText("Fragment added successfully", status = HttpStatusCode.Created)
    }

    get("/fragments") {
        val fragments = textPageService.getAllTextPages()
        call.respond(fragments)
    }

    get("/fragments/{id}") {
        val id = call.parameters["id"]?.toUUIDOrNull()
        val fragment = textPageService.getTextPageById(id)
        if (fragment != null) {
            call.respond(fragment)
        } else {
            call.respondText("Fragment not found", status = HttpStatusCode.NotFound)
        }
    }

    put("/fragments/{id}") {
        val id = call.parameters["id"]?.toUUIDOrNull() ?: return@put
        val updatedText = call.receive<TextPage>()
        if (textPageService.updateTextPage(updatedText.copy(id = id))) {
            call.respondText("Fragment updated successfully", status = HttpStatusCode.OK)
        } else {
            call.respondText("Fragment not modified", status = HttpStatusCode.NotModified)
        }

    }

    delete("/fragments/{id}") {
        val id = call.parameters["id"]?.toUUIDOrNull()
        if (textPageService.deleteTextPageById(id)) {
            call.respondText("Fragment deleted successfully", status = HttpStatusCode.OK)
        } else {
            call.respondText("Fragment not deleted", status = HttpStatusCode.NotModified)
        }
    }
}
