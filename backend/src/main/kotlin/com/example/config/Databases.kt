package com.example.config

import org.jetbrains.exposed.sql.Database

fun initDatabase(url: String, user: String, password: String, driver: String): Database {
    val database = Database.connect(url, driver, user, password)

    return database
}
