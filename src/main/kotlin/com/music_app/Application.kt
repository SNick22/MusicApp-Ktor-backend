package com.music_app

import com.music_app.plugins.*
import com.music_app.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt(), host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val tokenConfig = TokenConfig(
        issuer = "http://localhost",
        audience = "users",
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("SECRET")
    )

    configureDatabase()
    configureFirebase()
    configureKoin()
    configureSerialization()
    configureSecurity(tokenConfig)
    configureRouting(tokenConfig)
}
