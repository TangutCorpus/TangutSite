package com.example.repository

import com.example.model.TextFragment
import com.example.model.TextFragments
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import java.time.LocalDateTime

class TextFragmentRepositoryImpl(private val db: Database) : TextFragmentRepository {
    init {
        transaction {
            SchemaUtils.create(TextFragments)
        }
    }

    override suspend fun getTextFragmentById(id: Int): TextFragment? = transaction(db) {
        TextFragments.select(TextFragments.id)
            .where { TextFragments.id eq id }
            .mapNotNull { it.toTextFragment() }
            .singleOrNull()
    }

    override suspend fun addTextFragment(textFragment: TextFragment) = transaction(db) {
        TextFragments.insert {
            it[textId] = textFragment.textId
            it[content] = textFragment.content
            it[lineNumber] = textFragment.lineNumber
            it[comment] = textFragment.comment
            it[createdAt] = textFragment.createdAt?.let { LocalDateTime.parse(it) }
        }
    }

    override suspend fun updateTextFragment(textFragment: TextFragment) = transaction(db) {
        TextFragments.update({ TextFragments.id eq textFragment.id!! }) {
            it[textId] = textFragment.textId
            it[content] = textFragment.content
            it[lineNumber] = textFragment.lineNumber
            it[comment] = textFragment.comment
            it[createdAt] = textFragment.createdAt?.let { LocalDateTime.parse(it) }
        }
    }

    override suspend fun deleteTextFragmentById(id: Int) = transaction(db) {
        TextFragments.deleteWhere { TextFragments.id eq id }
    }

    override suspend fun getAllTextFragments(): List<TextFragment> = transaction(db) {
        TextFragments.selectAll()
            .map { it.toTextFragment() }
    }
}

private fun ResultRow.toTextFragment(): TextFragment {
    return TextFragment(
        id = this[TextFragments.id],
        textId = this[TextFragments.textId],
        lineNumber = this[TextFragments.lineNumber],
        content = this[TextFragments.content],
        comment = this[TextFragments.comment],
        createdAt = this[TextFragments.createdAt]?.toString()
    )
}