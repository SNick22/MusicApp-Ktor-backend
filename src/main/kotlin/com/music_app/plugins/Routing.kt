package com.music_app.plugins

import com.music_app.features.login.configureLoginRouting
import com.music_app.features.music.configureMusicRouting
import com.music_app.features.register.configureRegisterRouting
import com.music_app.security.token.TokenConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(tokenConfig: TokenConfig) {
    routing {
        authenticate {
            get("/") {
                call.respond(HttpStatusCode.OK)
            }
        }
        staticResources("/static", "assets")
        configureRegisterRouting()
        configureLoginRouting(tokenConfig)
        configureMusicRouting()
    }
}
