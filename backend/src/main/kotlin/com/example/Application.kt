package com.example

import com.example.config.*
import com.example.repository.*
import com.example.service.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    val config = environment.config

    val isProduction = config.property("ktor.deployment.mode").getString() == "production"
    val deploymentHosts = config.property("ktor.deployment.hosts").getString()
    val dbUrl = config.property("ktor.db.url").getString()
    val dbUser = config.property("ktor.db.user").getString()
    val dbPassword = config.property("ktor.db.password").getString()
    val dbDriver = config.property("ktor.db.driver").getString()
    val maxFileSize = config.property("ktor.http.request.maxFileSize").getString().toInt()
    val maxAge = config.property("ktor.security.maxAge").getString().toLong()

    val database = initDatabase(dbUrl, dbUser, dbPassword, dbDriver)

    val userRepository = UserRepository(database)
    val userService = UserService(userRepository)

    val sessionRepository = SessionRepository(database)
    val securityService = SecurityService(userRepository, sessionRepository)

    val textRepository = TextRepository(database)
    val textService = TextService(textRepository)

    val textPageRepository = TextPageRepository(database)
    val textPageService = TextPageService(textPageRepository)

    val searchService = SearchService(textRepository, textPageRepository)

    val imageRepository = ImageRepository()
    val imageService = ImageService(imageRepository)

    configureSecurity(isProduction, securityService, maxAge)
    configureSerialization(maxFileSize)
    configureExceptionHandling()
    configureMonitoring()
    configureHTTP(isProduction, deploymentHosts)
    configureRouting(userService, textService, searchService, textPageService, securityService, imageService)
}
