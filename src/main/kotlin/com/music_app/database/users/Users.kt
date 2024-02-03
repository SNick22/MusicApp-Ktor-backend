package com.music_app.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Users: Table("users") {

    private val id = Users.integer("user_id").autoIncrement()
    private val phoneNumber = Users.varchar("phone_number", 10).uniqueIndex()
    private val password = Users.varchar("password", 64)
    private val salt = Users.varchar("salt", 64)

    override val primaryKey: PrimaryKey = PrimaryKey(id)

    fun insert(user: UserDTO) {
        transaction {
            Users.insert {
                it[phoneNumber] = user.phoneNumber
                it[password] = user.password
                it[salt] = user.salt
            }
        }
    }

    fun fetchUserById(userId: Int): UserDTO? {
        return transaction {
            val userModel = Users.select { Users.id.eq(userId) }.singleOrNull()
            userModel?.let {
                UserDTO(
                    id = it[Users.id],
                    phoneNumber = it[phoneNumber],
                    password = it[password],
                    salt = it[salt]
                )
            }
        }
    }

    fun fetchUserByPhoneNumber(userPhoneNumber: String): UserDTO? {
        return transaction {
            val userModel = Users.select { phoneNumber.eq(userPhoneNumber) }.singleOrNull()
            userModel?.let {
                UserDTO(
                    id = it[Users.id],
                    phoneNumber = it[phoneNumber],
                    password = it[password],
                    salt = it[salt]
                )
            }
        }
    }
}
