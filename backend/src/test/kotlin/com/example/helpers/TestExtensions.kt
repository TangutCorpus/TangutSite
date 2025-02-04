package com.example.helpers

import com.example.model.Text
import com.example.model.TextPage
import com.example.model.User
import io.ktor.server.application.Application
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.ApplicationTestBuilder
import org.jetbrains.exposed.sql.Database

fun getH2Database(): Database {
    return Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
}

fun ApplicationTestBuilder.setupApp(
    defaultText: Text? = null,
    defaultTextPage: TextPage? = null,
    defaultUser: User? = null
) {
    environment {
        config = ApplicationConfig("application-test.conf")
    }
    application {
        testModule()
    }
}

fun Application.testModule() {
}

