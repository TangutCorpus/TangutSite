package com.example.repository

import com.example.model.Text
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
        Texts.selectAll().searchText(query).mapNotNull { it.toText() }
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


fun Query.searchText(query: String): Query {
    val rootNode: Node = RSQLParser().parse(query)
    val queryExpression = rootNode.accept(TextRSQLVisitor())
    return this.andWhere { queryExpression }
}

private class TextRSQLVisitor : NoArgRSQLVisitorAdapter<Op<Boolean>>() {
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
            "title" -> applyTextComparison(Texts.title, operator, argument)
            "metadata" -> applyTextComparison(Texts.metadata, operator, argument)
            else -> throw IllegalArgumentException("Unknown field: $selector")
        }
    }

    fun applyUUIDComparison(col: Column<UUID>, op: ComparisonOperator, arg: String): Op<Boolean> {
        val intValue = arg.toUUIDOrNull() ?: throw IllegalArgumentException("Invalid UUID: $arg")
        return when (op.symbol) {
            "==" -> col eq intValue
            "!=" -> col neq intValue
            else -> throw IllegalArgumentException("Unsupported operator: ${op.symbol}")
        }
    }

    fun applyTextComparison(col: Column<String>, op: ComparisonOperator, arg: String): Op<Boolean> {
        return when (op.symbol) {
            "==" -> col eq arg
            "!=" -> col neq arg
            "=in=" -> col like "%$arg%"
            else -> throw IllegalArgumentException("Unsupported operator: ${op.symbol}")
        }
    }
}