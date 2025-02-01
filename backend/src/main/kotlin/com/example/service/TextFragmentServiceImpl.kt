package com.example.service

import com.example.model.TextFragment
import com.example.repository.TextFragmentRepository
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import java.util.UUID

class TextFragmentServiceImpl(private val fragmentRepository: TextFragmentRepository) : TextFragmentService {
    override suspend fun getTextFragmentById(id: UUID?): TextFragment? {
        require(id != null) { "ID cannot be empty" }
        return fragmentRepository.getTextFragmentById(id)
            ?: throw NoSuchElementException("No fragment found with id $id")
    }

    override suspend fun addTextFragment(textFragment: TextFragment) {
        require(textFragment.contentXML.isNotBlank()) { "Fragment cannot be empty" }
        require(textFragment.canBeParsedToLocalDateTime()) { "Field 'createdAt': '${textFragment.createdAt}' cannot be parsed to LocalDateTime" }
        fragmentRepository.addTextFragment(textFragment)
    }

    override suspend fun updateTextFragment(textFragment: TextFragment): Boolean {
        require(textFragment.contentXML.isNotBlank()) { "Fragment cannot be empty" }
        require(textFragment.canBeParsedToLocalDateTime()) { "Field 'createdAt': '${textFragment.createdAt}' cannot be parsed to LocalDateTime" }
        return fragmentRepository.updateTextFragment(textFragment) != 0
    }

    override suspend fun deleteTextFragmentById(id: UUID?): Boolean {
        require(id != null) { "ID cannot be empty" }
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