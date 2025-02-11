package com.example.routes

import com.example.model.Text
import com.example.model.TextRequest
import com.example.service.TextService
import com.example.utils.toUUIDOrNull
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.UUID

fun Route.textRoutes(textService: TextService) {
    post("/texts") {
        val textRequest = call.receive<TextRequest>()
        val text = Text(id = UUID.randomUUID(), title = textRequest.title, metadata = textRequest.metadata, pageIds = textRequest.pageIds)
        val id = textService.addText(text)
        call.respondText(id.toString(), status = HttpStatusCode.Created)
    }

    get("/texts") {
        val texts = textService.getAllTexts()
        call.respond(texts)
    }

    get("/texts/{id}") {
        val id = call.parameters["id"]?.toUUIDOrNull()
        val text = textService.getTextById(id)
        if (text != null) {
            call.respond(text)
        } else {
            call.respondText("Text not found", status = HttpStatusCode.NotFound)
        }
    }

    put("/texts/{id}") {
        val id = call.parameters["id"]?.toUUIDOrNull()
        val updatedText = call.receive<Text>()
        if (textService.updateText(updatedText.copy(id = id!!))) {
            call.respondText("Text updated successfully", status = HttpStatusCode.OK)
        } else {
            call.respondText("Text not modified", status = HttpStatusCode.NotModified)
        }
    }

    delete("/texts/{id}") {
        val id = call.parameters["id"]?.toUUIDOrNull()
        if (textService.deleteTextById(id)) {
            call.respondText("Text deleted successfully", status = HttpStatusCode.OK)
        } else {
            call.respondText("Text not deleted", status = HttpStatusCode.NotModified)
        }
    }
}
