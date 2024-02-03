package com.music_app.features.register

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    @SerialName("phone_number")
    val phoneNumber: String,
    val password: String,
    @SerialName("notifications_token")
    val notificationsToken: String
)

@Serializable
data class RegisterResponse(
    @SerialName("sms_id")
    val smsId: Int
)

@Serializable
data class RegisterConfirmRequest(
    @SerialName("sms_id")
    val smsId: Int,
    val code: String
)

@Serializable
data class RegisterResendCodeRequest(
    @SerialName("sms_id")
    val smsId: Int,
)
