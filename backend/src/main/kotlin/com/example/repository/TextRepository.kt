package com.example.repository

import com.example.model.Text

interface TextRepository {
    suspend fun getTextById(id: Int): Text?
    suspend fun getTextsByQuery(query: String): List<Text>
    suspend fun addText(text: Text): Int
    suspend fun updateText(text: Text): Int
    suspend fun deleteTextById(id: Int): Int
    suspend fun getAllTexts(): List<Text>
}
