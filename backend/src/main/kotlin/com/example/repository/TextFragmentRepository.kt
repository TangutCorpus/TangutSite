package com.example.repository

import com.example.model.TextFragment
import java.util.UUID

interface TextFragmentRepository {
    suspend fun getTextFragmentById(id: UUID): TextFragment?
    suspend fun addTextFragment(textFragment: TextFragment): UUID
    suspend fun updateTextFragment(textFragment: TextFragment): Int
    suspend fun deleteTextFragmentById(id: UUID): Int
    suspend fun getAllTextFragments(): List<TextFragment>
}
