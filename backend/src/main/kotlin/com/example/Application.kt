package com.example

import com.example.config.configureExceptionHandling
import com.example.config.configureHTTP
import com.example.config.configureMonitoring
import com.example.config.configureSecurity
import com.example.config.configureSerialization
import com.example.config.initDatabase
import com.example.repository.TextRepositoryImpl
import com.example.repository.UserRepositoryImpl
import com.example.config.configureRouting
import com.example.service.SearchServiceImpl
import com.example.service.TextServiceImpl
import com.example.service.UserServiceImpl
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    val config = environment.config
    var database = initDatabase(config)

    var userRepository = UserRepositoryImpl(database)
    var userService = UserServiceImpl(userRepository)

    var textRepository = TextRepositoryImpl(database)
    var textService = TextServiceImpl(textRepository)

    var searchService = SearchServiceImpl(textRepository)

    configureRouting(userService, textService, searchService)
    configureExceptionHandling()
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureSecurity()
}
