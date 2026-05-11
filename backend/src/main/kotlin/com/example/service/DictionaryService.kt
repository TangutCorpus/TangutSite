package com.example.service

import com.example.model.DictionaryArticle
import com.example.repository.DictionaryRepository
import java.util.*

class DictionaryService(private val repository: DictionaryRepository) {
    fun getArticleById(id: UUID?): DictionaryArticle {
        require(id != null) { "ID cannot be empty" }
        return repository.getArticleById(id) ?: throw NoSuchElementException("Article $id not found")
    }

    fun addArticle(article: DictionaryArticle): UUID {
        require(article.character.isNotBlank()) { "Character symbol is required" }
        return repository.addArticle(article)
    }

    fun updateArticle(article: DictionaryArticle): Boolean {
        return repository.updateArticle(article) != 0
    }

    fun deleteArticle(id: UUID?): Boolean {
        require(id != null) { "ID cannot be empty" }
        return repository.deleteArticleById(id) != 0
    }

    fun getAllArticles(): List<DictionaryArticle> = repository.getAllArticles()

    fun searchArticles(query: String): List<DictionaryArticle> = repository.getArticlesByQuery(query)
}