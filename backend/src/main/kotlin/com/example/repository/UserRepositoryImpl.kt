package com.example.repository

import com.example.model.User
import org.jetbrains.exposed.sql.*
import java.util.UUID

class UserRepositoryImpl(private val database: Database) : UserRepository {
    override suspend fun createUser(user: User): UUID {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(
        id: UUID, user: User
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(id: UUID) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUsers(): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(i: UUID): User? {
        TODO("Not yet implemented")
    }

}
