package com.music_app.cache

object CacheStorage {

    private val userSmsMap = hashMapOf<Int, UserSms>()

    fun putUserSms(userSms: UserSms): Int {
        val smsId = getUniqueSmsId()
        userSmsMap[smsId] = userSms
        return smsId
    }

    fun updateUserSms(smsId: Int, userSms: UserSms) {
        userSmsMap[smsId] = userSms
    }

    fun getUserSms(smsId: Int): UserSms? = userSmsMap[smsId]

    fun deleteUserSms(smsId: Int): UserSms? = userSmsMap.remove(smsId)

    private fun getUniqueSmsId(): Int = userSmsMap.keys.maxOrNull()?.plus(1) ?: 1
}
