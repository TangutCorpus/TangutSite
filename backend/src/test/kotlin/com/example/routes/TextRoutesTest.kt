package com.example.routes

import com.example.helpers.DefaultParams
import com.example.helpers.jsonRequest
import com.example.helpers.setupApp
import com.example.model.Text
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class TextRoutesTest {
    private val text = Text(DefaultParams.textID, listOf(DefaultParams.textFragmentId), "", "", null)

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
        val response = client.get("/texts/${DefaultParams.textID}")
        assertEquals(HttpStatusCode.OK, response.status)
    }


    @Test
    fun `test GET non-existing text returns NotFound`() = testApplication {
        setupApp(defaultText = text)
        val response = client.get("/texts/${DefaultParams.invalidId}") {}
        assertEquals(HttpStatusCode.NotFound, response.status)
    }


    @Test
    fun `test PUT non-existing text returns NotFound`() = testApplication {
        setupApp(defaultText = text)
        val response = client.put("/texts/${DefaultParams.invalidId}") {
            jsonRequest(text)
        }
        assertEquals(HttpStatusCode.NotModified, response.status)
    }

    @Test
    fun `test PUT text updates text`() = testApplication {
        setupApp(defaultText = text)
        val response = client.put("/texts/${DefaultParams.textID}") {
            jsonRequest(text)
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `test DELETE text deletes text`() = testApplication {
        setupApp(defaultText = text)
        val response = client.delete("/texts/${DefaultParams.textID}") {
            jsonRequest(text)
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }
}
