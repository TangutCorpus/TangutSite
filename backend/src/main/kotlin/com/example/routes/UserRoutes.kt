package com.example.routes

import com.example.model.Text
import com.example.model.User
import com.example.service.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes(userService: UserService) {
    post("/users") {
        val user = call.receive<User>()
        userService.createUser(user)
        call.respondText("User created successfully", status = HttpStatusCode.Created)
    }

    get("/users/{id}") {
        val id = call.parameters["id"]?.toUUIDOrNull()
        val updatedUser = call.receive<User>()
        if (userService.updateUser(updatedUser.copy(id = id!!))) {
            call.respondText("User updated successfully", status = HttpStatusCode.OK)
        } else {
            call.respondText("User not modified", status = HttpStatusCode.NotModified)
        }
    }

    get("/users"){
        val users = userService.getAllUsers()
        call.respond(users)
    }

    put("/users/{id}") {
        val id = call.parameters["id"]?.toUUIDOrNull()
        val updatedUser = call.receive<User>()
        if (userService.updateUser(updatedUser.copy(id = id!!))) {
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
}
