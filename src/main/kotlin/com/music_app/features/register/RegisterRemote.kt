package com.music_app.features.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val phoneNumber: String,
    val password: String,
    val notificationsToken: String
)

@Serializable
data class RegisterResponse(
    val smsId: Int
)

@Serializable
data class RegisterConfirmRequest(
    val smsId: Int,
    val code: String
)

@Serializable
data class RegisterResendCodeRequest(
    val smsId: Int,
)
