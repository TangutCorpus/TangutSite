package com.example.service

import com.example.model.Text

interface SearchService {
    suspend fun search(query: String): List<Text>
    suspend fun returnAllSearchResults(): List<Text>
}