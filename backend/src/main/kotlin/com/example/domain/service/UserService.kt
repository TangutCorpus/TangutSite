package com.example.domain.service

import com.example.domain.model.ExposedUser

interface UserService {
    suspend fun getAllUsers(): List<ExposedUser>
    suspend fun getUserById(id: Int): ExposedUser?
    suspend fun addUser(user: ExposedUser)
    suspend fun updateUser(id: Int, user: ExposedUser): Boolean
    suspend fun deleteUser(id: Int): Boolean
}