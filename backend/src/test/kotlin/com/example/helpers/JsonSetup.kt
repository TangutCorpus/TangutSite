package com.example.helpers

import com.example.config.UUIDSerializer
import com.example.model.Text
import com.example.model.TextFragment
import com.example.model.User
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.util.UUID

@OptIn(ExperimentalSerializationApi::class)
val testJson = Json {
    serializersModule = SerializersModule {
        contextual(UUID::class, UUIDSerializer)
    }
    isLenient = true
    prettyPrint = true
    ignoreUnknownKeys = true
}

fun HttpRequestBuilder.jsonRequest(text: Text) {
    contentType(ContentType.Application.Json)
    setBody(testJson.encodeToString(text))
}

fun HttpRequestBuilder.jsonRequest(fragment: TextFragment) {
    contentType(ContentType.Application.Json)
    setBody(testJson.encodeToString(fragment))
}

fun HttpRequestBuilder.jsonRequest(user: User) {
    contentType(ContentType.Application.Json)
    setBody(testJson.encodeToString(user))
}