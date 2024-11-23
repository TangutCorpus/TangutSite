package com.example.service

import com.example.model.Text
import com.example.repository.TextRepository

class SearchServiceImpl(private val textRepository: TextRepository) : SearchService {
    override suspend fun search(query: String): List<Text> {
        return textRepository.getTextsByQuery(query)
    }

    override suspend fun returnAllSearchResults(): List<Text> {
        return textRepository.getAllTexts()
    }
}