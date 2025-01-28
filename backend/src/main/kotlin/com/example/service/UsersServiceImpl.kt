package com.example.service

import com.example.model.User
import com.example.repository.UserRepository
import java.util.UUID

class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override suspend fun getAllUsers(): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(id: UUID): User? {
        TODO("Not yet implemented")
    }

    override suspend fun createUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(
        id: UUID, user: User,
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(id: UUID): Boolean {
        TODO("Not yet implemented")
    }
}
