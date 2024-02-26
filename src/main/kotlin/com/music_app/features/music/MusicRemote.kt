package com.music_app.features.music

import kotlinx.serialization.Serializable

@Serializable
data class MusicResponse(
    val id: Int,
    val title: String,
    val version: String?,
    val duration: Long,
    val author: String,
    val audio: String,
    val video: String?,
    val smallCover: String?,
    val largeCover: String?
)

@Serializable
data class MusicRequest(
    val title: String,
    val version: String?,
    val duration: Long,
    val author: String,
    val audio: String,
    val video: String?,
    val smallCover: String?,
    val largeCover: String?
)
