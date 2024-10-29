package com.example.reposiotries

import com.example.model.ExposedUser
import com.example.repository.UserRepository
import com.example.repository.UserRepositoryImpl
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UserRepositoryTest {
    private lateinit var repository: UserRepository
    private val EXCEPTION_MESSAGE: String = "An operation is not implemented: Not yet implemented"

    @BeforeTest
    fun before() {
        var database = mockk<Database>(relaxed = true)
        repository = UserRepositoryImpl(database)
    }

    @Test
    fun `test createUser throws NotImplementedError`() = runBlocking {
        var user = ExposedUser("", 0)
        var exception = assertFailsWith<NotImplementedError> {
            repository.createUser(user)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `test readUser throws NotImplementedError`() = runBlocking {
        var exception = assertFailsWith<NotImplementedError> {
            repository.readUser(1)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `test updateUser throws NotImplementedError`() = runBlocking {
        var user = ExposedUser("", 0)
        var exception = assertFailsWith<NotImplementedError> {
            repository.updateUser(1, user)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `test deleteUser throws NotImplementedError`() = runBlocking {
        var exception = assertFailsWith<NotImplementedError> {
            repository.deleteUser(1)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `test getAllUsers throws NotImplementedError`() = runBlocking {
        var exception = assertFailsWith<NotImplementedError> {
            repository.getAllUsers()
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `test getUserById throws NotImplementedError`() = runBlocking {
        var exception = assertFailsWith<NotImplementedError> {
            repository.getUserById(1)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }
}
