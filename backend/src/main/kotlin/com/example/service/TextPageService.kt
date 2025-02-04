package com.example.service

import com.example.model.TextPage
import java.util.UUID

interface TextPageService {
    suspend fun getTextPageById(id: UUID?): TextPage?
    suspend fun addTextPage(textPage: TextPage)
    suspend fun updateTextPage(textPage: TextPage): Boolean
    suspend fun deleteTextPageById(id: UUID?): Boolean
    suspend fun getAllTextPages(): List<TextPage>
}
