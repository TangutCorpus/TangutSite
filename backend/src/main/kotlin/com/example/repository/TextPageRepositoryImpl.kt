package com.example.repository

import com.example.model.TextPage
import com.example.model.TextPages
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.LocalDateTime
import java.util.UUID

class TextPageRepositoryImpl(private val db: Database) : TextPageRepository {
    init {
        transaction {
            SchemaUtils.create(TextPages)
        }
    }

    override suspend fun getTextPageById(id: UUID): TextPage? = transaction(db) {
        TextPages.selectAll().where { TextPages.id eq id }.mapNotNull { it.toTextPage() }.singleOrNull()
    }

    override suspend fun addTextPage(textPage: TextPage) = transaction(db) {
        TextPages.insert {
            it[textId] = textPage.textId
            it[contentXML] = textPage.contentXML
            it[pageNumber] = textPage.pageNumber
            it[commentXML] = textPage.commentXML
            it[createdAt] = textPage.createdAt?.let { LocalDateTime.parse(it.toString()) }
        } get TextPages.id
    }

    override suspend fun updateTextPage(textPage: TextPage) = transaction(db) {
        TextPages.update({ TextPages.id eq textPage.id }) {
            it[textId] = textPage.textId
            it[contentXML] = textPage.contentXML
            it[pageNumber] = textPage.pageNumber
            it[commentXML] = textPage.commentXML
            it[createdAt] = textPage.createdAt?.let { LocalDateTime.parse(it.toString()) }
        }
    }

    override suspend fun deleteTextPageById(id: UUID) = transaction(db) {
        TextPages.deleteWhere { TextPages.id eq id }
    }

    override suspend fun getAllTextPages(): List<TextPage> = transaction(db) {
        TextPages.selectAll().map { it.toTextPage() }
    }
}

private fun ResultRow.toTextPage(): TextPage {
    return TextPage(id = this[TextPages.id],
        textId = this[TextPages.textId],
        pageNumber = this[TextPages.pageNumber],
        contentXML = this[TextPages.contentXML],
        commentXML = this[TextPages.commentXML],
        createdAt = this[TextPages.createdAt]?.let { LocalDate.parse(it.toString()) })
}
