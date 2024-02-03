package com.music_app.features.register

import com.google.firebase.messaging.FirebaseMessaging
import com.music_app.cache.CacheStorage
import com.music_app.cache.UserSms
import com.music_app.cache.toMessage
import com.music_app.database.users.UserDTO
import com.music_app.database.users.UsersController
import com.music_app.isValidCode
import com.music_app.isValidPassword
import com.music_app.isValidPhone
import com.music_app.security.hashing.HashingService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.configureRegisterRouting() {
    val hashingService by inject<HashingService>()
    val usersController by inject<UsersController>()

    post("register") {
        val request = call.receiveNullable<RegisterRequest>() ?: run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        if (!request.phoneNumber.isValidPhone()) {
            call.respond(HttpStatusCode.BadRequest, "Invalid phone number")
            return@post
        }

        if (!request.password.isValidPassword()) {
            call.respond(HttpStatusCode.BadRequest, "Password length must be > 8")
            return@post
        }

        if (request.notificationsToken.isBlank()) {
            call.respond(HttpStatusCode.BadRequest, "Invalid token")
            return@post
        }

        if (usersController.checkUserExists(request.phoneNumber)) {
            call.respond(HttpStatusCode.Conflict, "User already exist")
            return@post
        }

        val userSms = UserSms(
            phoneNumber = request.phoneNumber,
            password = request.password,
            code = (1000..9999).random().toString(),
            notificationsToken = request.notificationsToken
        )
        FirebaseMessaging.getInstance().send(userSms.toMessage())
        val smsId = CacheStorage.putUserSms(userSms)
        val response = RegisterResponse(smsId = smsId)
        call.respond(response)
    }

    post("register/confirm") {
        val request = call.receiveNullable<RegisterConfirmRequest>() ?: run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        if (!request.code.isValidCode()) {
            call.respond(HttpStatusCode.BadRequest, "Invalid code")
            return@post
        }

        if (CacheStorage.getUserSms(request.smsId)?.code != request.code) {
            call.respond(HttpStatusCode.Conflict, "Incorrect code")
            return@post
        }

        val userSms = CacheStorage.deleteUserSms(request.smsId)!!
        val saltedHash = hashingService.generateSaltedHash(userSms.password)
        val user = UserDTO(
            phoneNumber = userSms.phoneNumber,
            password = saltedHash.hash,
            salt = saltedHash.salt
        )
        usersController.registerUser(user)
        call.respond(HttpStatusCode.OK)
    }

    post("register/resend-code") {
        val request = call.receiveNullable<RegisterResendCodeRequest>() ?: run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val userSms = CacheStorage.getUserSms(request.smsId) ?: run {
            call.respond(HttpStatusCode.NotFound, "SMS with this id does not exist")
            return@post
        }
        val newUserSms = userSms.copy(code = (1000..9999).random().toString())
        CacheStorage.updateUserSms(
            smsId = request.smsId,
            userSms = newUserSms
        )
        FirebaseMessaging.getInstance().send(newUserSms.toMessage())
        call.respond(HttpStatusCode.OK)
    }
}
