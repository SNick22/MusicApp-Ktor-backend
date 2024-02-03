package com.music_app.plugins

import com.music_app.database.users.Users
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {
    val database = Database.connect(
        System.getenv("DB_URL"),
        driver = "org.postgresql.Driver",
        user = System.getenv("DB_USER"),
        password = System.getenv("DB_PASSWORD")
    )

    transaction(database) {
        SchemaUtils.create(Users)
    }
}
