package com.example.service

import com.example.model.User
import java.util.UUID

interface UserService {
    suspend fun getAllUsers(): List<User>
    suspend fun getUserById(id: UUID?): User?
    suspend fun createUser(user: User)
    suspend fun updateUser(id: UUID?, user: User): Boolean
    suspend fun deleteUserById(id: UUID?): Boolean
}
