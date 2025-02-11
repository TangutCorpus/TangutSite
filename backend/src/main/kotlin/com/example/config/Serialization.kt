package com.example.config

import io.ktor.http.content.PartData
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.dataconversion.DataConversion
import io.ktor.server.plugins.partialcontent.PartialContent
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult
import io.ktor.utils.io.readRemaining
import kotlinx.io.readByteArray
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.util.UUID

@OptIn(ExperimentalSerializationApi::class)
val jsonConfig = Json {
    serializersModule = SerializersModule {
        contextual(UUID::class, UUIDSerializer)
    }
    isLenient = true
    prettyPrint = true
    ignoreUnknownKeys = true
}

fun Application.configureSerialization(maxFileSize: Int) {
    install(ContentNegotiation) {
        json(jsonConfig)
    }

    install(PartialContent)

    install(DataConversion)

    install(RequestValidation) {
        validate<PartData.FileItem> { part ->
            val fileSize = part.provider().readRemaining().readByteArray().size
            if (fileSize > maxFileSize) {
                return@validate ValidationResult.Invalid("File is too big: $fileSize bytes")
            }
            ValidationResult.Valid
        }
    }
}

@ExperimentalSerializationApi
object UUIDSerializer : KSerializer<UUID> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }
}