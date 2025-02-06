package com.example.repository

import com.example.model.TextPage
import com.example.model.TextPages
import com.example.utils.toUUIDOrNull
import cz.jirutka.rsql.parser.RSQLParser
import cz.jirutka.rsql.parser.ast.AndNode
import cz.jirutka.rsql.parser.ast.ComparisonNode
import cz.jirutka.rsql.parser.ast.ComparisonOperator
import cz.jirutka.rsql.parser.ast.NoArgRSQLVisitorAdapter
import cz.jirutka.rsql.parser.ast.Node
import cz.jirutka.rsql.parser.ast.OrNode
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
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
            it[pageNumber] = textPage.pageNumber
            it[pureText] = textPage.pureText
            it[glossedTextXML] = textPage.glossedTextXML
            it[translationsXML] = textPage.translationsXML
        } get TextPages.id
    }

    override suspend fun updateTextPage(textPage: TextPage) = transaction(db) {
        TextPages.update({ TextPages.id eq textPage.id }) {
            it[textId] = textPage.textId
            it[pageNumber] = textPage.pageNumber
            it[pureText] = textPage.pureText
            it[glossedTextXML] = textPage.glossedTextXML
            it[translationsXML] = textPage.translationsXML
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
    return TextPage(
        id = this[TextPages.id],
        textId = this[TextPages.textId],
        pageNumber = this[TextPages.pageNumber],
        pureText = this[TextPages.pureText],
        glossedTextXML = this[TextPages.glossedTextXML],
        translationsXML = this[TextPages.translationsXML]
    )
}

fun Query.search(query: String): Query = transaction {
    val rootNode: Node = RSQLParser().parse(query)
    val queryExpression = rootNode.accept(ExposedRSQLVisitor())
    andWhere { queryExpression }
}

private class ExposedRSQLVisitor : NoArgRSQLVisitorAdapter<Op<Boolean>>() {
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
            "text" -> applyTextComparison(TextPages.pureText, operator, argument)
            "translation" -> applyTextComparison(TextPages.translationsXML, operator, argument)
            else -> throw IllegalArgumentException("Unknown field: $selector")
        }
    }

    private fun applyUUIDComparison(column: Column<UUID>, operator: ComparisonOperator, argument: String): Op<Boolean> {
        val intValue = argument.toUUIDOrNull() ?: throw IllegalArgumentException("Invalid UUID: $argument")
        return when (operator.symbol) {
            "==" -> column eq intValue
            "!=" -> column neq intValue
            else -> throw IllegalArgumentException("Unsupported operator: ${operator.symbol}")
        }
    }

    private fun applyTextComparison(
        column: Column<String>, operator: ComparisonOperator, argument: String
    ): Op<Boolean> {
        return when (operator.symbol) {
            "==" -> column eq argument
            "!=" -> column neq argument
            "=contains=" -> column like "%$argument%"
            else -> throw IllegalArgumentException("Unsupported operator: ${operator.symbol}")
        }
    }
}