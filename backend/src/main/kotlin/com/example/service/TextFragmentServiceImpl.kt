package com.example.service

import com.example.model.TextFragment
import com.example.repository.TextFragmentRepository

class TextFragmentServiceImpl(private val fragmentRepository: TextFragmentRepository) : TextFragmentService {
    override suspend fun getTextFragmentById(id: Int): TextFragment? {
        require(id >= 0) { "ID cannot be negative" }
        return fragmentRepository.getTextFragmentById(id)
            ?: throw NoSuchElementException("No fragment found with id $id")
    }

    override suspend fun addTextFragment(textFragment: TextFragment) {
        require(textFragment.content.isNotBlank() && textFragment.textId >= 0) { "Fragment cannot be empty" }
        fragmentRepository.addTextFragment(textFragment)
    }

    override suspend fun updateTextFragment(textFragment: TextFragment) {
        require(textFragment.content.isNotBlank() && textFragment.textId >= 0) { "Fragment cannot be empty" }
        fragmentRepository.updateTextFragment(textFragment)
    }

    override suspend fun deleteTextFragmentById(id: Int) {
        require(id >= 0) { "ID cannot be negative" }
        fragmentRepository.deleteTextFragmentById(id)
    }

    override suspend fun getAllTextFragments(): List<TextFragment> = fragmentRepository.getAllTextFragments()
}
