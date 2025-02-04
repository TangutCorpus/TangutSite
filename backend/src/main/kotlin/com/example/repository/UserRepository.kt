package com.example.repository

import com.example.model.User
import java.util.UUID

interface UserRepository {
    suspend fun createUser(user: User): UUID
    suspend fun updateUser(user: User): Int
    suspend fun deleteUserById(id: UUID): Int
    suspend fun getAllUsers(): List<User>
    suspend fun getUserById(id: UUID): User?
    suspend fun getUserByUsername(username: String): User?
    suspend fun getUserByEmail(email: String): User?
}
