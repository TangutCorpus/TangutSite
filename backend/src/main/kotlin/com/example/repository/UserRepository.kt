package com.example.repository

import com.example.model.User

interface UserRepository {
    suspend fun createUser(user: User): Int
    suspend fun updateUser(id: Int, user: User): Boolean
    suspend fun deleteUser(id: Int)
    suspend fun getAllUsers(): List<User>
    suspend fun getUserById(i: Int): User?
}
