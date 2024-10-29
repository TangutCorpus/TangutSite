package com.example.helpers

import com.example.config.initDatabase
import com.example.repository.TextRepositoryImpl
import com.example.repository.UserRepositoryImpl
import com.example.routes.configureRouting
import com.example.service.TextServiceImpl
import com.example.service.UserServiceImpl
import io.ktor.server.application.Application
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.ApplicationTestBuilder

fun ApplicationTestBuilder.setupApp() {

    environment {
        config = ApplicationConfig("application-test.conf")
    }
    application {
        module()
    }
}

fun Application.module() {
    val mockDatabase = initDatabase(environment.config)

    val userRepository = UserRepositoryImpl(mockDatabase)
    val userService = UserServiceImpl(userRepository)

    val textRepository = TextRepositoryImpl(mockDatabase)
    val textService = TextServiceImpl(textRepository)

    configureRouting(userService, textService)
}

