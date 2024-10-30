package com.example.service

import com.example.model.Text

interface TextService {
    suspend fun getTextById(id: Int): Text?
    suspend fun addText(text: Text)
    suspend fun updateText(text: Text): Boolean
    suspend fun deleteTextById(id: Int): Boolean
    suspend fun getAllTexts(): List<Text>
}
