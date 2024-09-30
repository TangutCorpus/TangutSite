package com.example.domain.repository

import com.example.domain.model.ExposedUser

interface UserRepository {
    suspend fun createUser(user: ExposedUser): Int
    suspend fun readUser(id: Int): ExposedUser?
    suspend fun updateUser(id: Int, user: ExposedUser): Boolean
    suspend fun deleteUser(id: Int)
    suspend fun getAllUsers(): List<ExposedUser>
    suspend fun getUserById(i: Int): ExposedUser?
    suspend fun addUser(user: ExposedUser)
}
