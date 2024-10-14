package com.example.repository

import com.example.model.Text

interface TextRepository {
    suspend fun getTextById(id: Int) : Text?;
    suspend fun addText(text: Text);
    suspend fun updateText(text: Text);
    suspend fun deleteTextById(id: Int);
    suspend fun getAllTexts(): List<Text>;
}