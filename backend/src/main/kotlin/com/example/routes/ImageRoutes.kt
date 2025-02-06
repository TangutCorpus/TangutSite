package com.example.routes

import com.example.service.ImageService
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.server.request.receiveMultipart
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.coroutines.coroutineScope

fun Route.imageRoutes(imageService: ImageService) {
    post("/image/upload") {
        val multipart = call.receiveMultipart()
        coroutineScope {
            multipart.forEachPart { part ->
                if (part is PartData.FileItem) {
                    imageService.store(part)
                    part.dispose()
                }
            }

        }
    }
}