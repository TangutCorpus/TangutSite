package com.example.helpers

import com.example.config.configureExceptionHandling
import com.example.config.configureHTTP
import com.example.config.configureMonitoring
import com.example.config.configureRouting
import com.example.config.configureSecurity
import com.example.config.configureSerialization
import com.example.model.Text
import com.example.service.TextService
import com.example.service.UserService
import io.ktor.server.application.Application
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.ApplicationTestBuilder

fun ApplicationTestBuilder.setupApp() {

    environment {
        config = ApplicationConfig("application-test.conf")
    }
    application {
        testModule()
    }
}

    val mockDatabase = initDatabase(environment.config)

    val userRepository = UserRepositoryImpl(mockDatabase)
    val userService = UserServiceImpl(userRepository)
fun Application.testModule() {

    configureRouting(userService, textService)
    configureExceptionHandling()
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureSecurity()
}

