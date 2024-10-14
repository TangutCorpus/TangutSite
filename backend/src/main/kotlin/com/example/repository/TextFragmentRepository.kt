package com.example.repository

import com.example.model.TextFragment

interface TextFragmentRepository {
    suspend fun getTextFragmentById(id: Int): TextFragment?;
    suspend fun addTextFragment(textFragment: TextFragment);
    suspend fun updateTextFragment(textFragment: TextFragment);
    suspend fun deleteTextFragmentById(id: Int);
    suspend fun getAllTextFragments(): List<TextFragment>;
}