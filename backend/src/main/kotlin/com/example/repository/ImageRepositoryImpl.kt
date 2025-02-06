package com.example.repository

import java.io.File
import java.util.UUID

class ImageRepositoryImpl : ImageRepository {
    private val uploadDir = File("uploads")

    init {
        if (!uploadDir.exists()) {
            uploadDir.mkdirs()
        }
    }

    override fun store(extension: String, image: ByteArray): UUID {
        val id = UUID.randomUUID()
        val file = File(uploadDir, "$id.$extension")
        file.writeBytes(image)
        return id
    }

    override fun get(id: UUID): File {
        return uploadDir.listFiles()?.find { it.nameWithoutExtension == id.toString() }
            ?: throw NoSuchElementException("Image not found")
    }
}
