package com.example.config

import io.ktor.server.config.ApplicationConfig
import org.jetbrains.exposed.sql.*

fun initDatabase(config: ApplicationConfig): Database {
    val dbUrl = config.property("ktor.db.url").getString()
    val dbUser = config.property("ktor.db.user").getString()
    val dbPassword = config.property("ktor.db.password").getString()
    val driver = config.property("ktor.db.driver").getString()

    var database = Database.connect(
        dbUrl,
        driver,
        user = dbUser,
        password = dbPassword
    )

    return database
}
