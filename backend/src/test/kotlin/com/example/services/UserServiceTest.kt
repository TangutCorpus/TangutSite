package com.example.services

import com.example.model.User
import com.example.repository.UserRepository
import com.example.service.UserService
import com.example.service.UserServiceImpl
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UserServiceTest {
    private lateinit var service: UserService
    private val EXCEPTION_MESSAGE: String = "An operation is not implemented: Not yet implemented"

    @BeforeTest
    fun setup() {
        var repository = mockk<UserRepository>(relaxed = true)
        service = UserServiceImpl(repository)
    }

    @Test
    fun `test createUser throws NotImplementedError`() = runBlocking {
        var user = User("", 0)
        var exception = assertFailsWith<NotImplementedError> {
            service.createUser(user)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `test updateUser throws NotImplementedError`() = runBlocking {
        var user = User("", 0)
        var exception = assertFailsWith<NotImplementedError> {
            service.updateUser(1, user)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `test deleteUser throws NotImplementedError`() = runBlocking {
        var exception = assertFailsWith<NotImplementedError> {
            service.deleteUser(1)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `test getAllUsers throws NotImplementedError`() = runBlocking {
        var exception = assertFailsWith<NotImplementedError> {
            service.getAllUsers()
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `test getUserById throws NotImplementedError`() = runBlocking {
        var exception = assertFailsWith<NotImplementedError> {
            service.getUserById(1)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }
}
