package com.example.service

import io.ktor.http.content.PartData

interface ImageService {
    suspend fun store(image: PartData.FileItem)
}