package com.example.service

import com.example.model.User
import com.example.repository.UserRepository
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import java.util.UUID

class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override suspend fun getAllUsers(): List<User> = userRepository.getAllUsers()

    override suspend fun getUserById(id: UUID?): User? {
        require(id != null) { "ID cannot be empty" }
        return userRepository.getUserById(id)
    }

    override suspend fun createUser(user: User) {
        require(user.canBeParsedToLocalDateTime()) { "Field 'createdAt': '${user.createdAt}' cannot be parsed to LocalDateTime" }

        userRepository.createUser(user)
    }

    override suspend fun updateUser(user: User): Boolean {
        require(user.nickname.length <= 255) { "User nickname cannot be longer than 255." }
        require(user.name.length <= 255) { "User name cannot be longer than 255." }
        require(user.canBeParsedToLocalDateTime()) { "Field 'createdAt': '${user.createdAt}' cannot be parsed to LocalDateTime" }

        return userRepository.updateUser(user) != 0

    }

    override suspend fun deleteUserById(id: UUID?): Boolean {
        require(id != null) { "ID cannot be empty" }
        return userRepository.deleteUserById(id) != 0
    }
}

fun User.canBeParsedToLocalDateTime(): Boolean {
    return try {
        createdAt?.atStartOfDayIn(TimeZone.currentSystemDefault())
        true
    } catch (_: Exception) {
        false
    }
}