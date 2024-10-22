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

    get("/texts/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id == null) {
            call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
            return@get
        }

        val text = textService.getTextById(id)
        if (text != null) {
            call.respondText(text.toString(), status = HttpStatusCode.OK)
        } else {
            call.respondText("Text not found", status = HttpStatusCode.NotFound)
        }
    }

    put("/texts/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id == null) {
            call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
            return@put
        }
        val updatedText = call.receive<Text>()
        textService.updateText(updatedText.copy(id = id))
        call.respondText("Text updated successfully", status = HttpStatusCode.OK)
    }

    delete("/texts/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id == null) {
            call.respondText("Invalid ID", status = HttpStatusCode.BadRequest)
            return@delete
        }
        textService.deleteTextById(id)
        call.respondText("Text deleted successfully", status = HttpStatusCode.OK)
    }
}
