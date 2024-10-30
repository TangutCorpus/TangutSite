package com.example.repository

import com.example.model.Text
import org.jetbrains.exposed.sql.statements.InsertStatement

interface TextRepository {
    suspend fun getTextById(id: Int): Text?
    suspend fun addText(text: Text): InsertStatement<Number>
    suspend fun updateText(text: Text): Int
    suspend fun deleteTextById(id: Int): Int
    suspend fun getAllTexts(): List<Text>
}
