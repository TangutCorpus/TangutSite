package com.example.service

import com.example.model.TextPage
import com.example.repository.TextPageRepository
import java.util.UUID

class TextPageServiceImpl(private val pageRepository: TextPageRepository) : TextPageService {
    override suspend fun getTextPageById(id: UUID?): TextPage? {
        require(id != null) { "ID cannot be empty" }
        return pageRepository.getTextPageById(id) ?: throw NoSuchElementException("No page found with id $id")
    }

    override suspend fun addTextPage(textPage: TextPage) {
        require(textPage.pureText.isNotBlank()) { "Page cannot be empty" }
        pageRepository.addTextPage(textPage)
    }

    override suspend fun updateTextPage(textPage: TextPage): Boolean {
        require(textPage.pureText.isNotBlank()) { "Page cannot be empty" }
        return pageRepository.updateTextPage(textPage) != 0
    }

    override suspend fun deleteTextPageById(id: UUID?): Boolean {
        require(id != null) { "ID cannot be empty" }
        return pageRepository.deleteTextPageById(id) != 0
    }

    override suspend fun getAllTextPages(): List<TextPage> = pageRepository.getAllTextPages()
}
