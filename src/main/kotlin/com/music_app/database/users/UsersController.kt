package com.music_app.database.users

class UsersController {

    fun registerUser(user: UserDTO) {
        Users.insert(user)
    }

    fun checkUserExists(phoneNumber: String): Boolean {
        val userModel = Users.fetchUserByPhoneNumber(phoneNumber)
        return userModel != null
    }
}
