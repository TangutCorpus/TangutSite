package com.example.service

import com.example.model.Text

class TextServiceImpl : TextService {
    override suspend fun getTextById(id: Int): Text? {
        TODO("Not yet implemented")
    }

    override suspend fun addText(text: Text) {
        TODO("Not yet implemented")
    }

    override suspend fun updateText(text: Text) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTextById(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTexts(): List<Text> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTextCommentById(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTextContentById(id: Int) {
        TODO("Not yet implemented")
    }
}