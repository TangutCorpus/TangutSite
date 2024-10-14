package com.example.service

import com.example.model.Text
import com.example.repository.TextRepository

class TextServiceImpl(private val textRepository: TextRepository) : TextService {

    override suspend fun getTextById(id: Int): Text? {
        require(id >= 0) { "ID cannot be negative" }
        return textRepository.getTextById(id) ?: throw NoSuchElementException("No text found with id $id")
    }

    override suspend fun addText(text: Text) {
        require(text.pureText.isNotBlank() && text.lineIds.isNotEmpty()) { "Text cannot be empty" }
        textRepository.addText(text)
    }

    override suspend fun updateText(text: Text) {
        require(text.pureText.isNotBlank() && text.lineIds.isNotEmpty()) { "Text cannot be empty" }
        textRepository.updateText(text)
    }

    override suspend fun deleteTextById(id: Int) {
        require(id >= 0) { "ID cannot be negative" }
        textRepository.deleteTextById(id)
    }

    override suspend fun getAllTexts(): List<Text> = textRepository.getAllTexts()

}
