package com.example.repository

import com.example.model.Text
import java.util.UUID

interface TextRepository {
    suspend fun getTextById(id: UUID): Text?
    suspend fun getTextsByQuery(query: String): List<Text>
    suspend fun addText(text: Text): UUID
    suspend fun updateText(text: Text): Int
    suspend fun deleteTextById(id: UUID): Int
    suspend fun getAllTexts(): List<Text>
}
