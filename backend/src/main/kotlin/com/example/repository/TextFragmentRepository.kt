package com.example.repository

import com.example.model.TextFragment
import org.jetbrains.exposed.sql.statements.InsertStatement

interface TextFragmentRepository {
    suspend fun getTextFragmentById(id: Int): TextFragment?
    suspend fun addTextFragment(textFragment: TextFragment): Int
    suspend fun updateTextFragment(textFragment: TextFragment): Int
    suspend fun deleteTextFragmentById(id: Int): Int
    suspend fun getAllTextFragments(): List<TextFragment>
}
