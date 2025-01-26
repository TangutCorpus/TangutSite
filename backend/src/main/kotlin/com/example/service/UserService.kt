package com.example.service

import com.example.model.User

interface UserService {
    suspend fun getAllUsers(): List<User>
    suspend fun getUserById(id: Int): User?
    suspend fun createUser(user: User)
    suspend fun updateUser(id: Int, user: User): Boolean
    suspend fun deleteUser(id: Int): Boolean
}
