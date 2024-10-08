package com.example.config

import org.jetbrains.exposed.sql.*

fun initDatabase(): Database {
    val database = Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = ""
    )
    return database
}
