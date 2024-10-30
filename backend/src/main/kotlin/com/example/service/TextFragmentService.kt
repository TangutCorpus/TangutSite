package com.example.service

import com.example.model.TextFragment

interface TextFragmentService {
    suspend fun getTextFragmentById(id: Int): TextFragment?
    suspend fun addTextFragment(textFragment: TextFragment)
    suspend fun updateTextFragment(textFragment: TextFragment)
    suspend fun deleteTextFragmentById(id: Int)
    suspend fun getAllTextFragments(): List<TextFragment>
}
