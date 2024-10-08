package com.example.config

import io.ktor.server.config.ApplicationConfig
import org.jetbrains.exposed.sql.*

fun initDatabase(config: ApplicationConfig): Database {
    val dbUrl = config.property("db.url").getString()
    val dbUser = config.property("db.user").getString()
    val dbPassword = config.property("db.password").getString()

    var database = Database.connect(
        dbUrl,
        driver = "org.postgresql.Driver",
        user = dbUser,
        password = dbPassword
    )

    return database
}
