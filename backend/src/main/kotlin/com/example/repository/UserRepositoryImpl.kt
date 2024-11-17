package com.example.repository

import com.example.model.ExposedUser
import org.jetbrains.exposed.sql.*

class UserRepositoryImpl(private val database: Database) : UserRepository {
    override suspend fun createUser(user: ExposedUser): Int {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(
        id: Int, user: ExposedUser
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUsers(): List<ExposedUser> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(i: Int): ExposedUser? {
        TODO("Not yet implemented")
    }

}
