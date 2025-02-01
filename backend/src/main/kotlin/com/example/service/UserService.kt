package com.example.service

import com.example.model.User
import java.util.UUID

interface UserService {
    suspend fun getAllUsers(): List<User>
    suspend fun getUserById(id: UUID?): User?
    suspend fun getUserByUsername(username: String?): User?
    suspend fun getUserByEmail(email: String?): User?
    suspend fun createUser(user: User?): UUID
    suspend fun updateUser(user: User?): Boolean
    suspend fun deleteUserById(id: UUID?): Boolean
}
