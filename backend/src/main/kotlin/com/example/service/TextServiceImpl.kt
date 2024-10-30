package com.example.service

import com.example.model.Text
import com.example.repository.TextRepository
import java.time.LocalDateTime

class TextServiceImpl(private val textRepository: TextRepository) : TextService {

    override suspend fun getTextById(id: Int): Text? {
        require(id >= 0) { "ID cannot be negative" }
        return textRepository.getTextById(id) ?: throw NoSuchElementException("No text found with id $id")
    }

    override suspend fun addText(text: Text) {
        require(text.pureText.isNotBlank() && text.lineIds.isNotEmpty()) { "Text cannot be empty" }
        require(text.canBeParsedToLocalDateTime()){ "Field 'createdAt': '${text.createdAt}' cannot be parsed to LocalDateTime" }
        textRepository.addText(text)
    }

    override suspend fun updateText(text: Text): Boolean {
        require(text.pureText.isNotBlank() && text.lineIds.isNotEmpty()) { "Text cannot be empty" }
        require(text.canBeParsedToLocalDateTime()) { "Field 'createdAt': '${text.createdAt}' cannot be parsed to LocalDateTime" }
        return textRepository.updateText(text) != 0
    }

    override suspend fun deleteTextById(id: Int): Boolean {
        require(id >= 0) { "ID cannot be negative" }
        return textRepository.deleteTextById(id) != 0
    }

    override suspend fun getAllTexts(): List<Text> = textRepository.getAllTexts()

}

fun Text.canBeParsedToLocalDateTime(): Boolean {
    return try {
        createdAt?.let { LocalDateTime.parse(it.toString()) }
        true
    } catch (_: Exception){
        false
    }
}