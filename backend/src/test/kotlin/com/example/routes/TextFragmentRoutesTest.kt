package com.example.routes

import com.example.helpers.setupApp
import com.example.model.Text
import com.example.model.TextFragment
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
import org.junit.Test
import kotlin.test.assertEquals

class TextFragmentRoutesTest {
    private val fragment = TextFragment(1, 1, 1, "", "", null)

    private fun HttpRequestBuilder.jsonRequest(fragment: TextFragment) {
        contentType(ContentType.Application.Json)
        setBody(Json.encodeToString(fragment))
    }

    @Test
    fun `test POST fragment creates fragment`() = testApplication {
        setupApp(defaultTextFragment = fragment)
        val response = client.post("/fragments") {
            jsonRequest(fragment)
        }
        assertEquals(HttpStatusCode.Created, response.status)
    }

    @Test
    fun `test GET all texts returns texts`() = testApplication {
        setupApp(defaultTextFragment = fragment)
        val response = client.get("/fragments")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `test GET text returns text`() = testApplication {
        setupApp(defaultTextFragment = fragment)
        val response = client.get("/fragments/1")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `test GET non-existing text returns NotFound`() = testApplication {
        setupApp(defaultTextFragment = fragment)
        val response = client.get("/fragments/2") {}
        assertEquals(HttpStatusCode.NotFound, response.status)
    }


    @Test
    fun `test PUT non-existing text returns NotFound`() = testApplication {
        setupApp(defaultTextFragment = fragment)
        val response = client.put("/fragments/2") {
            jsonRequest(fragment)
        }
        assertEquals(HttpStatusCode.NotModified, response.status)
    }

    @Test
    fun `test PUT text updates text`() = testApplication {
        setupApp(defaultTextFragment = fragment)
        val response = client.put("/fragments/1") {
            jsonRequest(fragment)
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `test DELETE text deletes text`() = testApplication {
        setupApp(defaultTextFragment = fragment)
        val response = client.delete("/fragments/1") {
            jsonRequest(fragment)
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }
}