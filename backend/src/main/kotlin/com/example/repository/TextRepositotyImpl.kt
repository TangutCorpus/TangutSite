package com.example.repository

import com.example.model.Text
import org.jetbrains.exposed.sql.Database

class TextRepositotyImpl(database: Database): TextRepository {
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
}