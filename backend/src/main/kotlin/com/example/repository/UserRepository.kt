package com.example.repository

import com.example.model.User
import java.util.UUID

interface UserRepository {
    suspend fun createUser(user: User): UUID
    suspend fun updateUser(user: User): Int
    suspend fun deleteUserById(id: UUID): Int
    suspend fun getAllUsers(): List<User>
    suspend fun getUserById(i: UUID): User?
}
