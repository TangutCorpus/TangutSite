package com.example.repository

import com.example.model.Text
import com.example.model.Texts
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.UUID


class TextRepositoryImpl(private val db: Database) : TextRepository {
    init {
        transaction(db) {
            SchemaUtils.create(Texts)
        }
    }

    override suspend fun getTextById(id: UUID): Text? = transaction(db) {
        Texts.selectAll().where { Texts.id eq id }.mapNotNull { it.toText() }.singleOrNull()
    }

    override suspend fun getTextsByQuery(query: String): List<Text> = transaction(db) {
        Texts.selectAll().search(query).mapNotNull { it.toText() }
    }

    override suspend fun addText(text: Text) = transaction(db) {
        Texts.insert {
            it[id] = text.id
            it[metadata] = text.metadata
            it[title] = text.title
            it[pageIds] = text.pageIds
        } get Texts.id
    }

    override suspend fun updateText(text: Text) = transaction(db) {
        Texts.update({ Texts.id eq text.id }) {
            it[id] = text.id
            it[metadata] = text.metadata
            it[title] = text.title
            it[pageIds] = text.pageIds
        }
    }

    override suspend fun deleteTextById(id: UUID) = transaction(db) {
        Texts.deleteWhere { Texts.id eq id }
    }

    override suspend fun getAllTexts(): List<Text> = transaction(db) {
        Texts.selectAll().map { it.toText() }
    }
}

private fun ResultRow.toText(): Text {
    return Text(
        id = this[Texts.id],
        title = this[Texts.title],
        metadata = this[Texts.metadata],
        pageIds = this[Texts.pageIds],
    )
}