package com.example.service

import com.example.repository.TextRepository
import javax.naming.directory.SearchResult

class SearchServiceImpl(textRepository: TextRepository) : SearchService {
    override suspend fun search(query: String): List<SearchResult> {
        TODO("Not yet implemented")
    }

    override suspend fun returnAllSearchResults(): Boolean {
        TODO("Not yet implemented")
    }
}