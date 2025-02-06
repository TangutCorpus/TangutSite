package com.example.repository

import com.example.model.User
import com.example.model.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID


class UserRepositoryImpl(private val db: Database) : UserRepository {
    init {
        transaction(db) {
            SchemaUtils.create(Users)
        }
    }

    override suspend fun createUser(user: User): UUID = transaction(db) {
        Users.insert {
            it[id] = user.id
            it[displayName] = user.displayName
            it[avatarUrl] = user.avatarUrl
            it[email] = user.email
            it[password] = user.password
            it[biography] = user.biography
            it[username] = user.username
            it[role] = user.role
        } get Users.id
    }

    override suspend fun updateUser(user: User): Int = transaction(db) {
        Users.update({ Users.id eq user.id }) {
            it[displayName] = user.displayName
            it[email] = user.email
            it[avatarUrl] = user.avatarUrl
            it[password] = user.password
            it[biography] = user.biography
            it[username] = user.username
            it[role] = user.role
        }
    }

    override suspend fun deleteUserById(id: UUID) = transaction(db) {
        Users.deleteWhere { Users.id eq id }
    }

    override suspend fun getAllUsers(): List<User> = transaction(db) {
        Users.selectAll().map { it.toUser() }
    }

    override suspend fun getUserById(id: UUID): User? = transaction(db) {
        Users.selectAll().where { Users.id eq id }.mapNotNull { it.toUser() }.singleOrNull()
    }

    override suspend fun getUserByUsername(username: String): User? = transaction(db) {
        Users.selectAll().where { Users.username eq username }.mapNotNull { it.toUser() }.singleOrNull()
    }

    override suspend fun getUserByEmail(email: String): User? = transaction(db) {
        Users.selectAll().where { Users.email eq email }.mapNotNull { it.toUser() }.singleOrNull()
    }
}

private fun ResultRow.toUser(): User {
    return User(
        id = this[Users.id],
        displayName = this[Users.displayName],
        avatarUrl = this[Users.avatarUrl],
        email = this[Users.email],
        biography = this[Users.biography],
        username = this[Users.username],
        password = this[Users.password],
        role = this[Users.role]
    )
}
