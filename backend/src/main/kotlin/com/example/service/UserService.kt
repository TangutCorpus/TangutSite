package com.example.service

import com.example.model.ExposedUser

interface UserService {
    suspend fun getAllUsers(): List<ExposedUser>
    suspend fun getUserById(id: Int): ExposedUser?
    suspend fun createUser(user: ExposedUser)
    suspend fun updateUser(id: Int, user: ExposedUser): Boolean
    suspend fun deleteUser(id: Int): Boolean
}
