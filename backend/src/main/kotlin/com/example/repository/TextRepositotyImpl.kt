package com.example.repository

import com.example.model.Text
import com.example.model.TextPages
import com.example.model.Texts
import com.example.utils.toUUIDOrNull
import cz.jirutka.rsql.parser.RSQLParser
import cz.jirutka.rsql.parser.ast.AndNode
import cz.jirutka.rsql.parser.ast.ComparisonNode
import cz.jirutka.rsql.parser.ast.ComparisonOperator
import cz.jirutka.rsql.parser.ast.NoArgRSQLVisitorAdapter
import cz.jirutka.rsql.parser.ast.Node
import cz.jirutka.rsql.parser.ast.OrNode
import kotlinx.datetime.LocalDate
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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
import java.time.LocalDateTime
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
            it[comment] = text.comment
            it[title] = text.title
            it[lineIds] = Json.encodeToString(text.lineIds)
            it[pureText] = text.pureText
            it[createdAt] = text.createdAt?.let { LocalDateTime.parse(it.toString()) }
        } get Texts.id
    }

    override suspend fun updateText(text: Text) = transaction(db) {
        Texts.update({ Texts.id eq text.id }) {
            it[comment] = text.comment
            it[title] = text.title
            it[lineIds] = Json.encodeToString(text.lineIds)
            it[pureText] = text.pureText
            it[TextPages.createdAt] = text.createdAt?.let { LocalDateTime.parse(it.toString()) }
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
        comment = this[Texts.comment],
        lineIds = Json.decodeFromString(this[Texts.lineIds]),
        pureText = this[Texts.pureText],
        createdAt = this[Texts.createdAt]?.let { LocalDate.parse(it.toString()) })
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
            "id" -> applyUUIDComparison(Texts.id, operator, argument)
            "comment" -> applyStringComparison(Texts.comment, operator, argument)
            "pureText" -> applyStringComparison(Texts.pureText, operator, argument)
            else -> throw IllegalArgumentException("Unknown field: $selector")
        }
    }

    private fun applyUUIDComparison(column: Column<UUID>, operator: ComparisonOperator, argument: String): Op<Boolean> {
        val intValue = argument.toUUIDOrNull() ?: throw IllegalArgumentException("Invalid integer: $argument")
        return when (operator.symbol) {
            "==" -> column eq intValue
            "!=" -> column neq intValue
            else -> throw IllegalArgumentException("Unsupported operator: ${operator.symbol}")
        }
    }

    private fun applyStringComparison(
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