package com.example.service

import com.example.model.TextFragment
import java.util.UUID

interface TextFragmentService {
    suspend fun getTextFragmentById(id: UUID?): TextFragment?
    suspend fun addTextFragment(textFragment: TextFragment)
    suspend fun updateTextFragment(textFragment: TextFragment): Boolean
    suspend fun deleteTextFragmentById(id: UUID?): Boolean
    suspend fun getAllTextFragments(): List<TextFragment>
}
