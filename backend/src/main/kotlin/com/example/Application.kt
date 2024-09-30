package com.example

import com.example.infrastructure.config.configureHTTP
import com.example.infrastructure.config.configureMonitoring
import com.example.web.routes.configureRouting
import com.example.infrastructure.config.configureSecurity
import com.example.infrastructure.config.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureSecurity()
    configureRouting()
}
