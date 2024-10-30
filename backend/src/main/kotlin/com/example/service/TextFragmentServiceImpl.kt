package com.example.service

import com.example.model.TextFragment
import com.example.repository.TextFragmentRepository
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

class TextFragmentServiceImpl(private val fragmentRepository: TextFragmentRepository) : TextFragmentService {
    override suspend fun getTextFragmentById(id: Int): TextFragment? {
        require(id >= 0) { "ID cannot be negative" }
        return fragmentRepository.getTextFragmentById(id)
            ?: throw NoSuchElementException("No fragment found with id $id")
    }

    override suspend fun addTextFragment(textFragment: TextFragment) {
        require(textFragment.contentXML.isNotBlank() && textFragment.textId >= 0) { "Fragment cannot be empty" }
        require(textFragment.canBeParsedToLocalDateTime()) { "Field 'createdAt': '${textFragment.createdAt}' cannot be parsed to LocalDateTime" }
        fragmentRepository.addTextFragment(textFragment)
    }

    override suspend fun updateTextFragment(textFragment: TextFragment): Boolean {
        require(textFragment.contentXML.isNotBlank() && textFragment.textId >= 0) { "Fragment cannot be empty" }
        require(textFragment.canBeParsedToLocalDateTime()) { "Field 'createdAt': '${textFragment.createdAt}' cannot be parsed to LocalDateTime" }
        return fragmentRepository.updateTextFragment(textFragment) != 0
    }

    override suspend fun deleteTextFragmentById(id: Int): Boolean {
        require(id >= 0) { "ID cannot be negative" }
        return fragmentRepository.deleteTextFragmentById(id) != 0
    }

    override suspend fun getAllTextFragments(): List<TextFragment> = fragmentRepository.getAllTextFragments()
}

fun TextFragment.canBeParsedToLocalDateTime(): Boolean {
    return try {
        createdAt?.atStartOfDayIn(TimeZone.currentSystemDefault())
        true
    } catch (_: Exception) {
        false
    }
}