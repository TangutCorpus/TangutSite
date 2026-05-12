package com.example.repository

import com.example.model.Text
import com.example.model.Texts
import com.example.utils.BaseRSQLVisitor
import com.example.utils.CustomIlikeOp
import com.example.utils.isRsql
import cz.jirutka.rsql.parser.RSQLParser
import cz.jirutka.rsql.parser.ast.ComparisonNode
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class TextRepository(private val db: Database) {
    init {
        transaction(db) {
            SchemaUtils.create(Texts)
        }
    }

    fun getTextById(id: UUID): Text? = transaction(db) {
        Texts.selectAll().where { Texts.id eq id }.mapNotNull { it.toText() }.singleOrNull()
    }

    fun getTextsByQuery(query: String): List<Text> = transaction(db) {
        val expression = if (query.isRsql()) {
            RSQLParser().parse(query).accept(TextRSQLVisitor())
        } else {
            buildGlobalTextMetadataSearch(query)
        }
        Texts.selectAll().where { expression }.map { it.toText() }
    }

    private fun buildGlobalTextMetadataSearch(query: String): Op<Boolean> {
        val p = "%$query%"
        return listOf<Op<Boolean>>(
            CustomIlikeOp(Texts.title, stringParam(p)), CustomIlikeOp(Texts.metadata, stringParam(p))
        ).reduce { acc, op -> acc or op }
    }

    fun addText(text: Text) = transaction(db) {
        Texts.insert {
            it[id] = text.id
            it[metadata] = text.metadata
            it[title] = text.title
            it[pageIds] = text.pageIds
        } get Texts.id
    }

    fun updateText(text: Text) = transaction(db) {
        Texts.update({ Texts.id eq text.id }) {
            it[id] = text.id
            it[metadata] = text.metadata
            it[title] = text.title
            it[pageIds] = text.pageIds
        }
    }

    fun deleteTextById(id: UUID) = transaction(db) {
        Texts.deleteWhere { Texts.id eq id }
    }

    fun getAllTexts(): List<Text> = transaction(db) {
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

private class TextRSQLVisitor : BaseRSQLVisitor() {
    override fun visit(node: ComparisonNode): Op<Boolean> {
        val selector = node.selector
        val arg = node.arguments[0]
        return when (selector) {
            "id" -> applyUUIDComparison(Texts.id, node.operator, arg)
            "title" -> applyTextComparison(Texts.title, node.operator, arg)
            "metadata" -> applyTextComparison(Texts.metadata, node.operator, arg)
            else -> throw IllegalArgumentException("Unknown field: $selector")
        }
    }
}