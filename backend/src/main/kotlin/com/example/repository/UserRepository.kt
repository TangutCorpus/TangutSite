package com.example.repository

import com.example.model.ExposedUser

interface UserRepository {
    suspend fun createUser(user: ExposedUser): Int
    suspend fun updateUser(id: Int, user: ExposedUser): Boolean
    suspend fun deleteUser(id: Int)
    suspend fun getAllUsers(): List<ExposedUser>
    suspend fun getUserById(i: Int): ExposedUser?
}
