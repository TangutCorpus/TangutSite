package com.example.routes

import com.example.model.Text
import com.example.service.TextService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.*

fun Route.textRoutes(textService: TextService) {
    post("/texts") {
        val text = call.receive<Text>()
        textService.addText(text)
        call.respondText("Text added successfully", status = HttpStatusCode.Created)
    }

    get("/texts") {
        val texts = textService.getAllTexts()
        call.respondText(texts.toString(), status = HttpStatusCode.OK)
    }

    get("/texts/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        val text = textService.getTextById(id)
        if (text != null) {
            call.respondText(text.toString(), status = HttpStatusCode.OK)
        } else {
            call.respondText("Text not found", status = HttpStatusCode.NotFound)
        }
    }

    put("/texts/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        val updatedText = call.receive<Text>()
        if (textService.updateText(updatedText.copy(id = id))) {
            call.respondText("Text updated successfully", status = HttpStatusCode.OK)
        } else {
            call.respondText("Text not modified", status = HttpStatusCode.NotModified)
        }

    }

    delete("/texts/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (textService.deleteTextById(id)) {
            call.respondText("Text deleted successfully", status = HttpStatusCode.OK)
        } else {
            call.respondText("Text not deleted", status = HttpStatusCode.NotModified)
        }
    }
}
