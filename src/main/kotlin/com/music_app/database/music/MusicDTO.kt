package com.music_app.database.music

data class MusicDTO(
    val id: Int = 0,
    val title: String,
    val version: String? = null,
    val duration: Long,
    val author: String,
    val audio: String,
    val video: String? = null,
    val smallCover: String? = null,
    val largeCover: String? = null
)
