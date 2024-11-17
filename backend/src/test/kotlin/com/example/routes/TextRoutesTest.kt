package com.example.routes

import com.example.helpers.setupApp
import com.example.model.Text
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.testApplication
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class TextRoutesTest {
    private val text = Text(1, "", listOf(0, 1, 2), "", null)

    private fun HttpRequestBuilder.jsonRequest(text: Text) {
        contentType(ContentType.Application.Json)
        setBody(Json.encodeToString(text))
    }

    @Test
    fun `test POST texts creates text`() = testApplication {
        setupApp(defaultText = text)
        val response = client.post("/texts") {
            jsonRequest(text)
        }
        assertEquals(HttpStatusCode.Created, response.status)
    }

    @Test
    fun `test GET all texts returns texts`() = testApplication {
        setupApp(defaultText = text)
        val response = client.get("/texts")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `test GET text returns text`() = testApplication {
        setupApp(defaultText = text)
        val response = client.get("/texts/1")
        assertEquals(HttpStatusCode.OK, response.status)
    }



    @Test
    fun `test GET non-existing text returns NotFound`() = testApplication {
        setupApp(defaultText = text)
        val response = client.get("/texts/2") {}
        assertEquals(HttpStatusCode.NotFound, response.status)
    }


    @Test
    fun `test PUT non-existing text returns NotFound`() = testApplication {
        setupApp(defaultText = text)
        val response = client.put("/texts/2") {
            jsonRequest(text)
        }
        assertEquals(HttpStatusCode.NotModified, response.status)
    }

    @Test
    fun `test PUT text updates text`() = testApplication {
        setupApp(defaultText = text)
        val response = client.put("/texts/1") {
            jsonRequest(text)
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `test DELETE text deletes text`() = testApplication {
        setupApp(defaultText = text)
        val response = client.delete("/texts/1") {
            jsonRequest(text)
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }
}
