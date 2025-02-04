package com.example.routes

import com.example.helpers.jsonRequest
import com.example.helpers.setupApp
import com.example.model.User
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import kotlinx.datetime.LocalDate
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class UserRoutesTest {
    private val user = User(
        id = UUID.randomUUID(),
        nickname = "testUser",
        email = "test@example.com",
        password = "password123",
        name = "Test User",
        biography = "A test user for demonstration purposes",
        role = "Editor",
        createdAt = LocalDate(2025, 1, 1)
    )

    @Test
    fun `test POST users returns 404 when not implemented`() = testApplication {
        setupApp(defaultUser = user)
        val response = client.post("/users") {
            jsonRequest(user)
        }
        assertEquals(HttpStatusCode.NotImplemented, response.status)
        assertEquals("Not yet implemented", response.bodyAsText())
    }

    @Test
    fun `test GET user returns 404 when not implemented`() = testApplication {
        setupApp(defaultUser = user)
        val response = client.get("/users/${user.id}")
        assertEquals(HttpStatusCode.NotImplemented, response.status)
        assertEquals("Not yet implemented", response.bodyAsText())
    }

    @Test
    fun `test PUT user returns 404 when not implemented`() = testApplication {
        setupApp(defaultUser = user)
        val response = client.put("/users/${user.id}") {
            jsonRequest(user)
        }
        assertEquals(HttpStatusCode.NotImplemented, response.status)
        assertEquals("Not yet implemented", response.bodyAsText())
    }

    @Test
    fun `test DELETE user returns 404 when not implemented`() = testApplication {
        setupApp(defaultUser = user)
        val response = client.delete("/users/${user.id}")
        assertEquals(HttpStatusCode.NotImplemented, response.status)
        assertEquals("Not yet implemented", response.bodyAsText())
    }
}