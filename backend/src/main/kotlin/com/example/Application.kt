package com.example

import com.example.config.configureHTTP
import com.example.config.configureMonitoring
import com.example.config.configureSecurity
import com.example.config.configureSerialization
import com.example.config.initDatabase
import com.example.repository.UserRepositoryImpl
import com.example.routes.configureRouting
import com.example.service.UserServiceImpl
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val config = environment.config
    var database = initDatabase(config)
    var userRepository = UserRepositoryImpl(database)
    var userService = UserServiceImpl(userRepository)

    configureRouting(userService)
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureSecurity()
    configureRouting(userService)
}
