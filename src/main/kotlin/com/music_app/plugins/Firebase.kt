package com.music_app.plugins

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.server.application.*

fun Application.configureFirebase() {
    val serviceAccountStream = this::class.java.classLoader.getResourceAsStream("serviceAccountKey.json")
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
        .build()
    FirebaseApp.initializeApp(options)
}
