package com.example.service

import com.example.model.Text
import com.example.repository.TextRepository
import java.util.*

class TextService(private val textRepository: TextRepository) {
    fun getTextById(id: UUID?): Text {
        require(id != null) { "ID cannot be empty" }
        return textRepository.getTextById(id) ?: throw NoSuchElementException("No text found with id $id")
    }

    fun addText(text: Text): UUID {
        require(text.title.isNotBlank()) { "Title cannot be empty" }
        return textRepository.addText(text)
    }

    fun updateText(text: Text): Boolean {
        require(text.title.isNotBlank()) { "Title cannot be empty" }
        return textRepository.updateText(text) != 0
    }

    fun deleteTextById(id: UUID?): Boolean {
        require(id != null) { "ID cannot be empty" }
        return textRepository.deleteTextById(id) != 0
    }

    fun getAllTexts(): List<Text> = textRepository.getAllTexts()
}
