package com.example.service

import io.ktor.http.content.PartData
import java.io.File
import java.util.UUID

interface ImageService {
    suspend fun store(file: PartData.FileItem): UUID
    fun getImage(id: UUID): File
}
