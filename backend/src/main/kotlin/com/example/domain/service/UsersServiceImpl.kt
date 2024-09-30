package com.example.domain.service

import com.example.domain.model.ExposedUser
import com.example.domain.repository.UserRepository

class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override suspend fun getAllUsers(): List<ExposedUser> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(id: Int): ExposedUser? {
        TODO("Not yet implemented")
    }

    override suspend fun addUser(user: ExposedUser) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(
        id: Int, user: ExposedUser
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(id: Int): Boolean {
        TODO("Not yet implemented")
    }


}
