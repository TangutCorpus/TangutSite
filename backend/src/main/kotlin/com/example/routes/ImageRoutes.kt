package com.example.routes

import com.example.service.ImageService
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.response.respondFile
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import kotlinx.coroutines.coroutineScope
import java.util.UUID

fun Route.imageRoutes(imageService: ImageService) {
    post("/image/upload") {
        val multipart = call.receiveMultipart()
        var imageId: UUID? = null

        coroutineScope {
            multipart.forEachPart { part ->
                if (part is PartData.FileItem) {
                    imageId = imageService.store(part)
                    part.dispose()
                }
                if (imageId != null) {
                    call.respond(HttpStatusCode.Created, mapOf("id" to imageId.toString()))
                } else {
                    call.respond(HttpStatusCode.BadRequest, "No image uploaded")
                }
            }
        }
    }

    get("/images/{id}") {
        val id = call.parameters["id"]?.let { UUID.fromString(it) } ?: throw BadRequestException("Invalid UUID format")

        val imageFile = imageService.getImage(id)
        call.respondFile(imageFile)
    }
}
