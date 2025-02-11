package com.example.service

import com.example.model.Text
import java.util.UUID

interface TextService {
    suspend fun getTextById(id: UUID?): Text?
    suspend fun addText(text: Text): UUID
    suspend fun updateText(text: Text): Boolean
    suspend fun deleteTextById(id: UUID?): Boolean
    suspend fun getAllTexts(): List<Text>
}
