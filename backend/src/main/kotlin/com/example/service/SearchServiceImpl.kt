package com.example.service

import com.example.model.Text
import com.example.repository.TextRepository
import javax.naming.directory.SearchResult

class SearchServiceImpl(private val textRepository: TextRepository) : SearchService {
    override suspend fun search(query: String): List<SearchResult> {
        TODO("Not yet implemented")
    }

    override suspend fun returnAllSearchResults(): List<Text> {
        return textRepository.getAllTexts()
    }
}