package com.example.service

import com.example.model.TextFragment
import com.example.repository.TextFragmentRepository
import java.time.LocalDateTime

class TextFragmentServiceImpl(private val fragmentRepository: TextFragmentRepository) : TextFragmentService {
    override suspend fun getTextFragmentById(id: Int): TextFragment? {
        require(id >= 0) { "ID cannot be negative" }
        return fragmentRepository.getTextFragmentById(id)
            ?: throw NoSuchElementException("No fragment found with id $id")
    }

    override suspend fun addTextFragment(textFragment: TextFragment) {
        require(textFragment.contentXML.isNotBlank() && textFragment.textId >= 0) { "Fragment cannot be empty" }
        require(textFragment.canBeParsedToLocalDateTime()){ "Field 'createdAt': '${textFragment.createdAt}' cannot be parsed to LocalDateTime" }
        fragmentRepository.addTextFragment(textFragment)
    }

    override suspend fun updateTextFragment(textFragment: TextFragment) {
        require(textFragment.contentXML.isNotBlank() && textFragment.textId >= 0) { "Fragment cannot be empty" }
        require(textFragment.canBeParsedToLocalDateTime()){ "Field 'createdAt': '${textFragment.createdAt}' cannot be parsed to LocalDateTime" }
        fragmentRepository.updateTextFragment(textFragment)
    }

    override suspend fun deleteTextFragmentById(id: Int) {
        require(id >= 0) { "ID cannot be negative" }
        fragmentRepository.deleteTextFragmentById(id)
    }

    override suspend fun getAllTextFragments(): List<TextFragment> = fragmentRepository.getAllTextFragments()
}

fun TextFragment.canBeParsedToLocalDateTime(): Boolean {
    return try {
        createdAt?.let { LocalDateTime.parse(it.toString()) }
        true
    } catch (_: Exception){
        false
    }
}