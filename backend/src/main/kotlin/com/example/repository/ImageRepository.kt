package com.example.repository

import java.io.File
import java.util.*

class ImageRepository {
    private val uploadDir = File(System.getProperty("user.dir"), "uploads").apply { mkdirs() }

    fun store(extension: String, image: ByteArray): UUID {
        var id = UUID.randomUUID()
        var file = File(uploadDir, "$id.$extension")
        while (file.exists()) {
            id = UUID.randomUUID()
            file = File(uploadDir, "$id.$extension")
        }
        file.outputStream().use { it.write(image) }
        return id
    }

    fun get(id: UUID): File {
        return uploadDir.listFiles()?.firstOrNull { it.nameWithoutExtension == id.toString() }
            ?: throw NoSuchElementException("Image not found")
    }
}
