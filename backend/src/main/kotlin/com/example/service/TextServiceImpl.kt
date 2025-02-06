package com.example.service

import com.example.model.Text
import com.example.repository.TextRepository
import java.util.UUID

class TextServiceImpl(private val textRepository: TextRepository) : TextService {
    override suspend fun getTextById(id: UUID?): Text? {
        require(id != null) { "ID cannot be empty" }
        return textRepository.getTextById(id) ?: throw NoSuchElementException("No text found with id $id")
    }

    override suspend fun addText(text: Text) {
        require(text.title.isNotBlank() && text.lineIds.isNotEmpty()) { "Title cannot be empty" }
        textRepository.addText(text)
    }

    override suspend fun updateText(text: Text): Boolean {
        require(text.title.isNotBlank() && text.lineIds.isNotEmpty()) { "Title cannot be empty" }
        return textRepository.updateText(text) != 0
    }

    override suspend fun deleteTextById(id: UUID?): Boolean {
        require(id != null) { "ID cannot be empty" }
        return textRepository.deleteTextById(id) != 0
    }

    override suspend fun getAllTexts(): List<Text> = textRepository.getAllTexts()
}
