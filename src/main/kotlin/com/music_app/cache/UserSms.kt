package com.music_app.cache

import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification

data class UserSms(
    val phoneNumber: String,
    val password: String,
    val notificationsToken: String,
    val code: String
)

fun UserSms.toMessage(): Message {
    return Message.builder()
        .setToken(this.notificationsToken)
        .setNotification(
            Notification.builder()
                .setTitle("Подтверждение номера")
                .setBody("Код: ${this.code}")
                .build()
        )
        .build()
}
