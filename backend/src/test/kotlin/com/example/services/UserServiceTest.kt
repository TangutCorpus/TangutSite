package com.example.services

import com.example.model.ExposedUser
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
    private lateinit var setvice: UserService
    private val EXCEPTION_MESSAGE: String = "An operation is not implemented: Not yet implemented"

    @BeforeTest
    fun setup() {
        var repository = mockk<UserRepository>(relaxed = true)
        setvice = UserServiceImpl(repository)
    }

    @Test
    fun `test createUser throws NotImplementedError`() = runBlocking {
        var user = ExposedUser("", 0)
        var exception = assertFailsWith<NotImplementedError> {
            setvice.createUser(user)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `test updateUser throws NotImplementedError`() = runBlocking {
        var user = ExposedUser("", 0)
        var exception = assertFailsWith<NotImplementedError> {
            setvice.updateUser(1, user)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `test deleteUser throws NotImplementedError`() = runBlocking {
        var exception = assertFailsWith<NotImplementedError> {
            setvice.deleteUser(1)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `test getAllUsers throws NotImplementedError`() = runBlocking {
        var exception = assertFailsWith<NotImplementedError> {
            setvice.getAllUsers()
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun `test getUserById throws NotImplementedError`() = runBlocking {
        var exception = assertFailsWith<NotImplementedError> {
            setvice.getUserById(1)
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }
}
