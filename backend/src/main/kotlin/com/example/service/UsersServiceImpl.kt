package com.example.service

import com.example.model.User
import com.example.repository.UserRepository

class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override suspend fun getAllUsers(): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(id: Int): User? {
        TODO("Not yet implemented")
    }

    override suspend fun createUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(
        id: Int, user: User,
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}
