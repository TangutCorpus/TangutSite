package com.example.service

import com.example.model.TextPage
import com.example.repository.TextPageRepository
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import java.util.UUID

class TextPageServiceImpl(private val pageRepository: TextPageRepository) : TextPageService {
    override suspend fun getTextPageById(id: UUID?): TextPage? {
        require(id != null) { "ID cannot be empty" }
        return pageRepository.getTextPageById(id)
            ?: throw NoSuchElementException("No page found with id $id")
    }

    override suspend fun addTextPage(textPage: TextPage) {
        require(textPage.contentXML.isNotBlank()) { "Page cannot be empty" }
        require(textPage.canBeParsedToLocalDateTime()) { "Field 'createdAt': '${textPage.createdAt}' cannot be parsed to LocalDateTime" }
        pageRepository.addTextPage(textPage)
    }

    override suspend fun updateTextPage(textPage: TextPage): Boolean {
        require(textPage.contentXML.isNotBlank()) { "Page cannot be empty" }
        require(textPage.canBeParsedToLocalDateTime()) { "Field 'createdAt': '${textPage.createdAt}' cannot be parsed to LocalDateTime" }
        return pageRepository.updateTextPage(textPage) != 0
    }

    override suspend fun deleteTextPageById(id: UUID?): Boolean {
        require(id != null) { "ID cannot be empty" }
        return pageRepository.deleteTextPageById(id) != 0
    }

    override suspend fun getAllTextPages(): List<TextPage> = pageRepository.getAllTextPages()
}

fun TextPage.canBeParsedToLocalDateTime(): Boolean {
    return try {
        createdAt?.atStartOfDayIn(TimeZone.currentSystemDefault())
        true
    } catch (_: Exception) {
        false
    }
}