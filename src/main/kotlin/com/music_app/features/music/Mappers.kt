package com.music_app.features.music

import com.music_app.database.music.MusicDTO

fun MusicDTO.toMusicResponse(): MusicResponse = MusicResponse(
    id = this.id,
    title = this.title,
    version = this.version,
    duration = this.duration,
    author = this.author,
    audio = "static/${this.audio}",
    video = this.video?.let { "static/$it" },
    smallCover = this.smallCover?.let { "static/$it" },
    largeCover = this.largeCover?.let { "static/$it" }
)

fun MusicRequest.toMusicDTO(): MusicDTO = MusicDTO(
    title = this.title,
    version = this.version,
    duration = this.duration,
    author = this.author,
    audio = this.audio,
    video = this.video,
    smallCover = this.smallCover,
    largeCover = this.largeCover
)
