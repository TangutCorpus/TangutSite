package com.example.routes

import com.example.helpers.DefaultParams
import com.example.helpers.setupApp
import com.example.model.Text
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SearchRoutesTest {
    private val text = Text(DefaultParams.textID, listOf(DefaultParams.textFragmentId), "Sample Comment", "Sample Text", null)

    @Test
    fun `test GET search with non-matching query`() = testApplication {
        setupApp(defaultText = text)
        val response = client.get("/search") {
            parameter("query", "comment=='Nonexistent'")
        }
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(response.bodyAsText(), "[]")
    }

    @Test
    fun `test GET search with AND condition`() = testApplication {
        setupApp(defaultText = text)
        val response = client.get("/search") {
            parameter("query", "comment=='Sample Comment' and pureText=='Sample Text'")
        }
        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue(response.bodyAsText().contains("Sample Text"))
    }

    @Test
    fun `test GET search without params`() = testApplication {
        setupApp(defaultText = text)
        val response = client.get("/search")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(response.bodyAsText(), Json.encodeToString(listOf<Text>(text)))
    }
}
