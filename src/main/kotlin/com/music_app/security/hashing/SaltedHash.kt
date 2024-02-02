package com.music_app.security.hashing

data class SaltedHash(
    val hash: String,
    val salt: String
)
