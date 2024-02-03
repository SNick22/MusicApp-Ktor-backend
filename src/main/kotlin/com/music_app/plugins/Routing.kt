package com.music_app.plugins

import com.music_app.features.register.configureRegisterRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        configureRegisterRouting()
    }
}
