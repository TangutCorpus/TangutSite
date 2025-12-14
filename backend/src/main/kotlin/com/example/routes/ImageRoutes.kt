package com.example.routes

import com.example.service.ImageService
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.imageRoutes(imageService: ImageService) {
    post("/image/upload") {
        val multipart = call.receiveMultipart()
        var imageId: UUID? = null

        multipart.forEachPart { part ->
            if (part is PartData.FileItem) {
                if (imageId != null) {
                    throw BadRequestException("Only one file is allowed per request")
                }
                imageId = imageService.store(part)
            }
            part.dispose()
        }

        if (imageId != null) {
            call.respond(HttpStatusCode.Created, mapOf("id" to imageId.toString()))
        } else {
            call.respond(HttpStatusCode.BadRequest, "No image uploaded")
        }
    }

    get("/images/{id}") {
        val id = call.parameters["id"]?.let { UUID.fromString(it) } ?: throw BadRequestException("Invalid UUID format")

        val imageFile = imageService.getImage(id)
        call.respondFile(imageFile)
    }
}
