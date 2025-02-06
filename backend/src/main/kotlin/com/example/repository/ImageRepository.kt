package com.example.repository

import java.io.File
import java.util.UUID

interface ImageRepository {
    fun store(extension: String, image: ByteArray): UUID
    fun get(id: UUID): File
}
