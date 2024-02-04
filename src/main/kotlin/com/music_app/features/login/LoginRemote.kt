package com.music_app.features.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val phoneNumber: String,
    val password: String,
)

@Serializable
data class LoginResponse(
    val token: String
)
