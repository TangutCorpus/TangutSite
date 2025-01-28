package com.example.repository

import com.example.model.User
import com.example.model.Users
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
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
            it[name] = user.name
            it[email] = user.email
            it[password] = user.password
            it[biography] = user.biography
            it[nickname] = user.nickname
            it[createdAt] = user.createdAt?.let { LocalDateTime.parse(it.toString()) }
        } get Users.id
    }

    override suspend fun updateUser(id: UUID, user: User): Int = transaction(db) {
        Users.update({ Users.id eq user.id }) {
            it[name] = user.name
            it[email] = user.email
            it[password] = user.password
            it[biography] = user.biography
            it[nickname] = user.nickname
            it[createdAt] = user.createdAt?.let { LocalDateTime.parse(it.toString()) }
        }
    }

    override suspend fun deleteUserById(id: UUID) = transaction(db) {
        Users.deleteWhere { Users.id eq id }
    }

    override suspend fun getAllUsers(): List<User> = transaction(db){
        Users.selectAll().map { it.toUser() }
    }

    override suspend fun getUserById(id: UUID): User? = transaction(db) {
        Users.selectAll().where { Users.id eq id }.mapNotNull { it.toUser() }.singleOrNull()
    }
}

private fun ResultRow.toUser(): User {
    return User(id = this[Users.id],
        name = this[Users.name],
        email = this[Users.email],
        biography = this[Users.biography],
        nickname = this[Users.nickname],
        password = this[Users.password],
        role = this[Users.role],
        createdAt = this[Users.createdAt]?.let { LocalDate.parse(it.toString()) })
}
