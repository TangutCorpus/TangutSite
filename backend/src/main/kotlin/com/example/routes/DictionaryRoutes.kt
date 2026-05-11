package com.example.routes

import com.example.model.DictionaryArticle
import com.example.service.DictionaryService
import com.example.utils.toUUIDOrNull
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.dictionaryRoutes(service: DictionaryService) {
    route("/dict") {
        get("/articles") {
            call.respond(service.getAllArticles())
        }

        get("/articles/{id}") {
            val id = call.parameters["id"]?.toUUIDOrNull()
            try {
                call.respond(service.getArticleById(id))
            } catch (e: NoSuchElementException) {
                call.respond(HttpStatusCode.NotFound, e.message ?: "Not found")
            }
        }

        post("/articles") {
            val article = call.receive<DictionaryArticle>()
            val id = service.addArticle(article.copy(id = UUID.randomUUID()))
            call.respond(HttpStatusCode.Created, id.toString())
        }

        put("/articles/{id}") {
            val id = call.parameters["id"]?.toUUIDOrNull() ?: return@put
            val article = call.receive<DictionaryArticle>()
            if (service.updateArticle(article.copy(id = id))) {
                call.respond(HttpStatusCode.OK, "Article updated")
            } else {
                call.respond(HttpStatusCode.NotModified)
            }
        }

        delete("/articles/{id}") {
            val id = call.parameters["id"]?.toUUIDOrNull()
            if (service.deleteArticle(id)) {
                call.respond(HttpStatusCode.OK, "Deleted")
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}