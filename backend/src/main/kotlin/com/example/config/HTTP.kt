package com.example.config

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureHTTP(isProduction: Boolean, allowedHosts: String) {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)

        val allowedOrigins = allowedHosts.split(',').map { it.trim() }.filter { it.isNotEmpty() }

        if (isProduction && allowedOrigins.isNotEmpty()) {
            allowedOrigins.forEach { host ->
                allowHost(host, schemes = listOf("https"))
            }
        } else {
            anyHost()
        }
    }
}
