package com.example.service

import com.example.model.DictionaryArticle
import com.example.model.Text
import com.example.model.TextPage
import com.example.repository.DictionaryRepository
import com.example.repository.TextPageRepository
import com.example.repository.TextRepository

class SearchService(private val textRepository: TextRepository, private val textPageRepository: TextPageRepository, private val dictionaryRepository: DictionaryRepository) {
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

    fun searchDictionary(query: String): List<DictionaryArticle> {
        return dictionaryRepository.getArticlesByQuery(query)
    }

    fun getAllDictionaryResults(): List<DictionaryArticle> {
        return dictionaryRepository.getAllArticles()
    }
}