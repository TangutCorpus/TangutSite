package com.example.service

import com.example.model.Text
import com.example.model.TextPage
import com.example.repository.TextPageRepository
import com.example.repository.TextRepository

class SearchService(private val textRepository: TextRepository, private val textPageRepository: TextPageRepository) {
    fun searchTexts(query: String): List<Text> {
        return textRepository.getTextsByQuery(query)
    }

    fun searchTextPages(query: String): List<TextPage> {
        return textPageRepository.getTextPagesByQuery(query)
    }

    fun returnAllTextSearchResults(): List<Text> {
        return textRepository.getAllTexts()
    }

    fun returnAllTextPageSearchResults(): List<TextPage> {
        return textPageRepository.getAllTextPages()
    }
}