package com.example.routes
//
import com.example.model.TextFragment
import com.example.service.TextFragmentService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import kotlin.text.toIntOrNull

fun Route.textFragmentRoutes(textFragmentService: TextFragmentService) {
    post("/fragments") {
        val fragment = call.receive<TextFragment>()
        textFragmentService.addTextFragment(fragment)
        call.respondText("Fragment added successfully", status = HttpStatusCode.Created)
    }

    get("/fragments") {
        val fragments = textFragmentService.getAllTextFragments()
        call.respond(fragments)
    }

    get("/fragments/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        val fragment = textFragmentService.getTextFragmentById(id)
        if (fragment != null) {
            call.respond(fragment)
        } else {
            call.respondText("Fragment not found", status = HttpStatusCode.NotFound)
        }
    }

    put("/fragments/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        val updatedText = call.receive<TextFragment>()
        if (textFragmentService.updateTextFragment(updatedText.copy(id = id))) {
            call.respondText("Fragment updated successfully", status = HttpStatusCode.OK)
        } else {
            call.respondText("Fragment not modified", status = HttpStatusCode.NotModified)
        }

    }

    delete("/fragments/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (textFragmentService.deleteTextFragmentById(id)) {
            call.respondText("Fragment deleted successfully", status = HttpStatusCode.OK)
        } else {
            call.respondText("Fragment not deleted", status = HttpStatusCode.NotModified)
        }
    }
}
