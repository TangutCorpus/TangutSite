package com.example

import com.example.config.configureExceptionHandling
import com.example.config.configureHTTP
import com.example.config.configureMonitoring
import com.example.config.configureRouting
import com.example.config.configureSecurity
import com.example.config.configureSerialization
import com.example.config.initDatabase
import com.example.repository.ImageRepositoryImpl
import com.example.repository.RefreshTokenRepositoryImpl
import com.example.repository.TextPageRepositoryImpl
import com.example.repository.TextRepositoryImpl
import com.example.repository.UserRepositoryImpl
import com.example.service.ImageServiceImpl
import com.example.service.SearchServiceImpl
import com.example.service.SecurityServiceImpl
import com.example.service.TextPageServiceImpl
import com.example.service.TextServiceImpl
import com.example.service.UserServiceImpl
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    val config = environment.config
    val maxFileSize = config.property("ktor.http.request.maxFileSize").getString().toInt()
    var database = initDatabase(config)

    var userRepository = UserRepositoryImpl(database)
    var userService = UserServiceImpl(userRepository)

    var textRepository = TextRepositoryImpl(database)
    var textService = TextServiceImpl(textRepository)

    var textPageRepository = TextPageRepositoryImpl(database)
    var textPageService = TextPageServiceImpl(textPageRepository)

    var searchService = SearchServiceImpl(textRepository)

    var refreshTokenRepository = RefreshTokenRepositoryImpl(database)
    var securityService = SecurityServiceImpl(userRepository, refreshTokenRepository, config)

    var imageRepository = ImageRepositoryImpl()
    var imageService = ImageServiceImpl(imageRepository)

    configureRouting(userService, textService, searchService, textPageService, securityService, imageService)
    configureExceptionHandling()
    configureSerialization(maxFileSize)
    configureMonitoring()
    configureHTTP()
    configureSecurity(securityService)
}
