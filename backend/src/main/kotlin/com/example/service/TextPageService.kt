package com.example.service

import com.example.model.TextPage
import com.example.repository.TextPageRepository
import java.util.*

class TextPageService(private val pageRepository: TextPageRepository) {
    fun getTextPageById(id: UUID?): TextPage {
        require(id != null) { "ID cannot be empty" }
        return pageRepository.getTextPageById(id) ?: throw NoSuchElementException("No page found with id $id")
    }

    fun addTextPage(textPage: TextPage): UUID {
        require(textPage.pureText.isNotBlank()) { "Page cannot be empty" }
        return pageRepository.addTextPage(textPage)
    }

    fun updateTextPage(textPage: TextPage): Boolean {
        require(textPage.pureText.isNotBlank()) { "Page cannot be empty " }
        return pageRepository.updateTextPage(textPage) != 0
    }

    fun deleteTextPageById(id: UUID?): Boolean {
        require(id != null) { "ID cannot be empty" }
        return pageRepository.deleteTextPageById(id) != 0
    }

    fun getAllTextPages(): List<TextPage> = pageRepository.getAllTextPages()
}
