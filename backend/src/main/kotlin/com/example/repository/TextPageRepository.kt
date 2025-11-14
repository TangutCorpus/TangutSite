package com.example.repository

import com.example.model.TextPage
import com.example.model.TextPages
import com.example.model.Texts
import com.example.utils.toUUIDOrNull
import cz.jirutka.rsql.parser.RSQLParser
import cz.jirutka.rsql.parser.ast.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class TextPageRepository(private val db: Database) {
    init {
        transaction {
            SchemaUtils.create(TextPages)
        }
    }

    fun getTextPageById(id: UUID): TextPage? = transaction(db) {
        TextPages.selectAll().where { TextPages.id eq id }.mapNotNull { it.toTextPage() }.singleOrNull()
    }

    fun getTextPagesByQuery(query: String): List<TextPage> = transaction(db) {
        TextPages.selectAll().searchTextPages(query).mapNotNull { it.toTextPage() }
    }

    fun addTextPage(textPage: TextPage) = transaction(db) {
        TextPages.insert {
            it[id] = textPage.id
            it[textId] = textPage.textId
            it[pageNumber] = textPage.pageNumber
            it[pureText] = textPage.pureText
            it[imagesIDs] = textPage.imagesIDs
            it[glossedTextXML] = textPage.glossedTextXML
            it[translationsXML] = textPage.translationsXML
        } get TextPages.id
    }

    fun updateTextPage(textPage: TextPage) = transaction(db) {
        TextPages.update({ TextPages.id eq textPage.id }) {
            it[id] = textPage.id
            it[textId] = textPage.textId
            it[pageNumber] = textPage.pageNumber
            it[pureText] = textPage.pureText
            it[imagesIDs] = textPage.imagesIDs
            it[glossedTextXML] = textPage.glossedTextXML
            it[translationsXML] = textPage.translationsXML
        }
    }

    fun deleteTextPageById(id: UUID) = transaction(db) {
        TextPages.deleteWhere { TextPages.id eq id }
    }

    fun getAllTextPages(): List<TextPage> = transaction(db) {
        TextPages.selectAll().map { it.toTextPage() }
    }
}

private fun ResultRow.toTextPage(): TextPage {
    return TextPage(
        id = this[TextPages.id],
        textId = this[TextPages.textId],
        imagesIDs = this[TextPages.imagesIDs],
        pageNumber = this[TextPages.pageNumber],
        pureText = this[TextPages.pureText],
        glossedTextXML = this[TextPages.glossedTextXML],
        translationsXML = this[TextPages.translationsXML]
    )
}

fun Query.searchTextPages(query: String): Query {
    val rootNode: Node = RSQLParser().parse(query)
    val queryExpression = rootNode.accept(TextPageRSQLVisitor())
    return this.andWhere { queryExpression }
}

private class TextPageRSQLVisitor : NoArgRSQLVisitorAdapter<Op<Boolean>>() {
    override fun visit(node: AndNode): Op<Boolean> {
        val expressions = node.children.map { it.accept(this) }
        return expressions.reduce { acc, op -> acc and op }
    }

    override fun visit(node: OrNode): Op<Boolean> {
        val expressions = node.children.map { it.accept(this) }
        return expressions.reduce { acc, op -> acc or op }
    }

    override fun visit(node: ComparisonNode): Op<Boolean> {
        val selector = node.selector
        val operator = node.operator
        val argument = node.arguments[0]

        return when (selector) {
            "id" -> applyUUIDComparison(TextPages.id, operator, argument)
            "gloss" -> applyTextComparison(TextPages.glossedTextXML, operator, argument)
            "pureText" -> applyTextComparison(TextPages.pureText, operator, argument)
            "translation" -> applyTextComparison(TextPages.translationsXML, operator, argument)
            else -> throw IllegalArgumentException("Unknown field: $selector")
        }
    }

    fun applyUUIDComparison(column: Column<UUID>, operator: ComparisonOperator, argument: String): Op<Boolean> {
        val intValue = argument.toUUIDOrNull() ?: throw IllegalArgumentException("Invalid UUID: $argument")
        return when (operator.symbol) {
            "==" -> column eq intValue
            "!=" -> column neq intValue
            else -> throw IllegalArgumentException("Unsupported operator: ${operator.symbol}")
        }
    }

    fun applyTextComparison(
        column: Column<String>, operator: ComparisonOperator, argument: String
    ): Op<Boolean> {
        return when (operator.symbol) {
            "==" -> column eq argument
            "!=" -> column neq argument
            "=in=" -> column like "%$argument%"
            else -> throw IllegalArgumentException("Unsupported operator: ${operator.symbol}")
        }
    }
}