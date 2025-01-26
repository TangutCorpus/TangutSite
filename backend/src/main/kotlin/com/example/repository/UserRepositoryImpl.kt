package com.example.repository

import com.example.model.User
import org.jetbrains.exposed.sql.*

class UserRepositoryImpl(private val database: Database) : UserRepository {
    override suspend fun createUser(user: User): Int {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(
        id: Int, user: User
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUsers(): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(i: Int): User? {
        TODO("Not yet implemented")
    }

}
