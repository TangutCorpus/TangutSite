package com.example.routes

import com.example.model.ExposedUser
import com.example.model.UserRoles
import com.example.model.updateFromExposedUser
import com.example.plugins.hasAnyRole
import com.example.service.UserService
import com.example.utils.toUUIDOrNull
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes(userService: UserService) {
    hasAnyRole(UserRoles.EDITOR) {
        get("/users/{id}") {
            val id = call.parameters["id"]?.toUUIDOrNull()
            userService.getUserById(id)?.let { user -> call.respond(HttpStatusCode.OK, user) }
                ?: call.respondText("User not modified", status = HttpStatusCode.NotModified)
        }

        put("/users/{id}") {
            val id = call.parameters["id"]?.toUUIDOrNull()
            val receivedUser = call.receive<ExposedUser>()
            var user = userService.getUserById(id)?.updateFromExposedUser(receivedUser)
            if (userService.updateUser(user)) {
                call.respondText("User updated successfully", status = HttpStatusCode.OK)
            } else {
                call.respondText("User not modified", status = HttpStatusCode.NotModified)
            }
        }

        delete("/users/{id}") {
            val id = call.parameters["id"]?.toUUIDOrNull()
            if (userService.deleteUserById(id)) {
                call.respondText("User deleted successfully", status = HttpStatusCode.OK)
            } else {
                call.respondText("User not deleted", status = HttpStatusCode.NotModified)
            }
        }

        get("/users") {
            val users = userService.getAllUsers()
            call.respond(HttpStatusCode.OK, users)
        }
    }
}
