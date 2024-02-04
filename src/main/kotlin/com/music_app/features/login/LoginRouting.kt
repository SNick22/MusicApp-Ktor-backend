package com.music_app.features.login

import com.music_app.database.users.UsersController
import com.music_app.isValidPassword
import com.music_app.isValidPhone
import com.music_app.security.hashing.HashingService
import com.music_app.security.hashing.SaltedHash
import com.music_app.security.token.TokenClaim
import com.music_app.security.token.TokenConfig
import com.music_app.security.token.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.configureLoginRouting(tokenConfig: TokenConfig) {
    val hashingService by inject<HashingService>()
    val usersController by inject<UsersController>()
    val tokenService by inject<TokenService>()

    post("login") {
        val request = call.receiveNullable<LoginRequest>() ?: run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        if (!request.phoneNumber.isValidPhone()) {
            call.respond(HttpStatusCode.BadRequest, "Invalid phone number")
            return@post
        }

        if (!request.password.isValidPassword()) {
            call.respond(HttpStatusCode.BadRequest, "Password length must be >= 8")
            return@post
        }

        val user = usersController.getUser(request.phoneNumber) ?: run {
            call.respond(HttpStatusCode.Conflict, "User don't registered")
            return@post
        }
        val saltedHash = SaltedHash(hash = user.password, salt = user.salt)
        val isPasswordsMatch = hashingService.verify(value = request.password, saltedHash = saltedHash)

        if (!isPasswordsMatch) {
            call.respond(HttpStatusCode.Conflict, "Incorrect password")
            return@post
        }

        val token = tokenService.generate(
            config = tokenConfig,
            TokenClaim(
                name = "userId",
                value = user.id.toString()
            )
        )

        call.respond(LoginResponse(token))
    }
}
