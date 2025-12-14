package com.example.service

import com.example.model.User
import com.example.repository.UserRepository
import java.util.*

class UserService(private val userRepository: UserRepository) {
    fun getAllUsers(): List<User> = userRepository.getAllUsers()

    fun getUserById(id: UUID?): User? {
        require(id != null) { "ID cannot be empty" }
        return userRepository.getUserById(id)
    }

    fun getUserByUsername(nickname: String?): User? {
        require(!nickname.isNullOrBlank()) { "ID cannot be empty" }
        return userRepository.getUserByUsername(nickname)
    }

    fun getUserByEmail(email: String?): User? {
        require(!email.isNullOrBlank()) { "Email cannot be empty" }
        return userRepository.getUserByEmail(email)
    }

    fun createUser(user: User?): UUID {
        requireNotNull(user) { "User format is incorrect" }
        return userRepository.createUser(user)
    }

    fun updateUser(user: User?): Boolean {
        requireNotNull(user) { "User format is incorrect" }
        require(user.username.length <= 255) { "User nickname cannot be longer than 255." }
        require(user.displayName.length <= 255) { "User name cannot be longer than 255." }
        return userRepository.updateUser(user) != 0
    }

    fun deleteUserById(id: UUID?): Boolean {
        require(id != null) { "ID cannot be empty" }
        return userRepository.deleteUserById(id) != 0
    }
}
