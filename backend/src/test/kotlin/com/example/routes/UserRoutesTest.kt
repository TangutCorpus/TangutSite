package com.example.routes

import com.example.helpers.setupApp
import com.example.model.ExposedUser
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class UserRoutesTest {
    @Test
    fun `test POST users returns 404 when not implemented`() = testApplication {
        setupApp()

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/users") {
            contentType(ContentType.Application.Json)
            setBody(ExposedUser("Test", 20))
        }
        assertEquals(HttpStatusCode.NotImplemented, response.status)
        assertEquals("Not yet implemented", response.bodyAsText())
    }

    @Test
    fun `test GET user returns 404 when not implemented`() = testApplication {
        setupApp()

        val response = client.get("/users/1")
        assertEquals(HttpStatusCode.NotImplemented, response.status)
        assertEquals("Not yet implemented", response.bodyAsText())
    }

    @Test
    fun `test PUT user returns 404 when not implemented`() = testApplication {
        setupApp()

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.put("/users/1") {
            contentType(ContentType.Application.Json)
            setBody(ExposedUser("Test", 20))
        }
        assertEquals(HttpStatusCode.NotImplemented, response.status)
        assertEquals("Not yet implemented", response.bodyAsText())
    }

    @Test
    fun `test DELETE user returns 404 when not implemented`() = testApplication {
        setupApp()

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.delete("/users/1")
        assertEquals(HttpStatusCode.NotImplemented, response.status)
        assertEquals("Not yet implemented", response.bodyAsText())
    }

}

