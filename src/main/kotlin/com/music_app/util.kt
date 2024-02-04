package com.music_app

fun String.isDigitsOnly(): Boolean {
    this.forEach { char ->
        if (!char.isDigit()) {
            return false
        }
    }
    return true
}

fun String.isValidPhone(): Boolean {
    return this.isDigitsOnly() && this.length == 10
}

fun String.isValidPassword(): Boolean {
    return this.length >= 8
}

fun String.isValidCode(): Boolean {
    return this.isDigitsOnly() && this.length == 4
}
