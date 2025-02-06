package com.example.repository

import java.io.File
import java.util.UUID

class ImageRepositoryImpl : ImageRepository {
    override fun store(extension: String, image: ByteArray): Unit {
        val uploadDir = File("uploads")
        if (!uploadDir.exists()) {
            uploadDir.mkdirs()
        }
        val file = File(uploadDir, UUID.randomUUID().toString() + '.' + extension)
        file.writeBytes(image)
    }
}