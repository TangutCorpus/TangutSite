package com.example.service

import javax.naming.directory.SearchResult

interface SearchService {
    suspend fun search(query: String): List<SearchResult>
    suspend fun returnAllSearchResults(): Boolean
}