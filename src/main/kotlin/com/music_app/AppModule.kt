package com.music_app

import com.music_app.database.music.MusicController
import com.music_app.database.users.UsersController
import com.music_app.security.hashing.HashingService
import com.music_app.security.hashing.SHA256HashingService
import com.music_app.security.token.JwtTokenService
import com.music_app.security.token.TokenService
import org.koin.dsl.module

val appModule = module {
    factory<HashingService> { SHA256HashingService() }
    factory<TokenService> { JwtTokenService() }
    factory { UsersController() }
    factory { MusicController() }
}
