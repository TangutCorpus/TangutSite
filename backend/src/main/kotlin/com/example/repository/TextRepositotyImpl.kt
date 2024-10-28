package com.example.repository

import com.example.model.Text
import com.example.model.TextFragments
import com.example.model.Texts
import kotlinx.datetime.LocalDate
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.update
import java.time.LocalDateTime


class TextRepositoryImpl(private val db: Database) : TextRepository {

    init {
        transaction(db) {
            SchemaUtils.create(Texts)
        }
    }

    override suspend fun getTextById(id: Int): Text? = transaction(db) {
        Texts.select(Texts.id)
            .where { Texts.id eq id }
            .mapNotNull { it.toText() }
            .singleOrNull()
    }

    override suspend fun addText(text: Text) = transaction(db) {
        Texts.insert {
            it[comment] = text.comment
            it[lineIds] = Json.encodeToString(text.lineIds)
            it[pureText] = text.pureText
            it[TextFragments.createdAt] = text.createdAt?.let { it -> LocalDateTime.parse(it.toString()) }        }
    }

    override suspend fun updateText(text: Text) = transaction(db) {
        Texts.update({ Texts.id eq text.id!! }) {
            it[comment] = text.comment
            it[lineIds] = Json.encodeToString(text.lineIds)
            it[pureText] = text.pureText
            it[TextFragments.createdAt] = text.createdAt?.let { LocalDateTime.parse(it.toString()) }
        }
    }

    override suspend fun deleteTextById(id: Int) = transaction(db) {
        Texts.deleteWhere { Texts.id eq id }
    }

    override suspend fun getAllTexts(): List<Text> = transaction(db) {
        Texts.selectAll()
            .map { it.toText() }
    }
}

private fun ResultRow.toText(): Text {
    return Text(
        id = this[Texts.id],
        comment = this[Texts.comment],
        lineIds = Json.decodeFromString(this[Texts.lineIds]),
        pureText = this[Texts.pureText],
        createdAt = this[Texts.createdAt]?.let { LocalDate.parse(it.toString()) }
    )
}
