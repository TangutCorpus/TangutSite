package com.example.service

import com.example.model.Text
import javax.naming.directory.SearchResult

interface SearchService {
    suspend fun search(query: String): List<SearchResult>
    suspend fun returnAllSearchResults(): List<Text>
}