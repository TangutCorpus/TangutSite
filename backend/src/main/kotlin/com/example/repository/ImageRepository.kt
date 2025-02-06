package com.example.repository

interface ImageRepository {
    fun store(extension: String, image: ByteArray): Unit
}