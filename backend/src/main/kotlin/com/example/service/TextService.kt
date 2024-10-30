package com.example.service

import com.example.model.Text

interface TextService {
    suspend fun getTextById(id: Int): Text?
    suspend fun addText(text: Text)
    suspend fun updateText(text: Text)
    suspend fun deleteTextById(id: Int)
    suspend fun getAllTexts(): List<Text>
}
