package com.music_app.features.music

import com.music_app.database.music.MusicController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import org.koin.ktor.ext.inject

fun Routing.configureMusicRouting() {
    val musicController by inject<MusicController>()

    authenticate {
        get("music") {
            val limit = call.parameters["limit"]!!.toInt()
            val offset = call.parameters["offset"]!!.toLong()
            val response = musicController.getMusics(limit, offset).map { it.toMusicResponse() }
            call.respond(response)
        }

        put("music") {
            val request = call.receiveNullable<MusicRequest>() ?: run {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }

            musicController.insertMusic(request.toMusicDTO())
            call.respond(HttpStatusCode.OK)
        }
    }
}
