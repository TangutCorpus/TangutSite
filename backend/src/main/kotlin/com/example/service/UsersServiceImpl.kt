package com.example.service

import com.example.model.ExposedUser
import com.example.repository.UserRepository

class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override suspend fun getAllUsers(): List<ExposedUser> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(id: Int): ExposedUser? {
        TODO("Not yet implemented")
    }

    override suspend fun createUser(user: ExposedUser) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(
        id: Int, user: ExposedUser,
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}
