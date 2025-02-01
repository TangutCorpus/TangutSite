package com.example.service

import com.example.model.Text
import com.example.repository.TextRepository
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import java.util.UUID

class TextServiceImpl(private val textRepository: TextRepository) : TextService {
    override suspend fun getTextById(id: UUID?): Text? {
        require(id != null) { "ID cannot be empty" }
        return textRepository.getTextById(id) ?: throw NoSuchElementException("No text found with id $id")
    }

    override suspend fun addText(text: Text) {
        require(text.pureText.isNotBlank() && text.lineIds.isNotEmpty()) { "Text cannot be empty" }
        require(text.canBeParsedToLocalDateTime()) { "Field 'createdAt': '${text.createdAt}' cannot be parsed to LocalDateTime" }
        textRepository.addText(text)
    }

    override suspend fun updateText(text: Text): Boolean {
        require(text.pureText.isNotBlank() && text.lineIds.isNotEmpty()) { "Text cannot be empty" }
        require(text.canBeParsedToLocalDateTime()) { "Field 'createdAt': '${text.createdAt}' cannot be parsed to LocalDateTime" }
        return textRepository.updateText(text) != 0
    }

    override suspend fun deleteTextById(id: UUID?): Boolean {
        require(id != null) { "ID cannot be empty" }
        return textRepository.deleteTextById(id) != 0
    }

    override suspend fun getAllTexts(): List<Text> = textRepository.getAllTexts()
}

fun Text.canBeParsedToLocalDateTime(): Boolean {
    return try {
        createdAt?.atStartOfDayIn(TimeZone.currentSystemDefault())
        true
    } catch (_: Exception) {
        false
    }
}