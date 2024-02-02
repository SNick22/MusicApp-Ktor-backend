package com.music_app

import com.music_app.plugins.configureRouting
import com.music_app.plugins.configureSecurity
import com.music_app.plugins.configureSerialization
import com.music_app.security.hashing.SHA256HashingService
import com.music_app.security.token.JwtTokenService
import com.music_app.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(
        System.getenv("DB_URL"),
        driver = "org.postgresql.Driver",
        user = System.getenv("DB_USER"),
        password = System.getenv("DB_PASSWORD")
    )

    embeddedServer(Netty, port = 8081, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = "http://localhost",
        audience = "users",
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("SECRET")
    )
    val hashingService = SHA256HashingService()

    configureRouting()
    configureSerialization()
    configureSecurity(tokenConfig)
}
