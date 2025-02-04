package com.example.repository

import com.example.model.TextPage
import java.util.UUID

interface TextPageRepository {
    suspend fun getTextPageById(id: UUID): TextPage?
    suspend fun addTextPage(textPage: TextPage): UUID
    suspend fun updateTextPage(textPage: TextPage): Int
    suspend fun deleteTextPageById(id: UUID): Int
    suspend fun getAllTextPages(): List<TextPage>
}
