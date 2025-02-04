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

    override suspend fun getUserByUsername(nickname: String?): User? {
        require(nickname != null && !nickname.isBlank()) { "ID cannot be empty" }
        return userRepository.getUserByUsername(nickname)
    }

    override suspend fun getUserByEmail(email: String?): User? {
        require(email != null && !email.isBlank()) { "Email cannot be empty" }
        return userRepository.getUserByEmail(email)
    }

    override suspend fun createUser(user: User?): UUID {
        requireNotNull(user) { "User format is incorrect" }
        require(user.canBeParsedToLocalDateTime()) { "Field 'createdAt': '${user.createdAt}' cannot be parsed to LocalDateTime" }
        return userRepository.createUser(user)
    }

    override suspend fun updateUser(user: User?): Boolean {
        requireNotNull(user) { "User format is incorrect" }
        require(user.username.length <= 255) { "User nickname cannot be longer than 255." }
        require(user.displayName.length <= 255) { "User name cannot be longer than 255." }
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

