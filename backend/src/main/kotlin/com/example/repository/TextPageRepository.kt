package com.example.repository

import com.example.model.TextPage
import com.example.model.TextPages
import com.example.model.Texts
import com.example.utils.BaseRSQLVisitor
import com.example.utils.CustomIlikeOp
import com.example.utils.isRsql
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
        val expression = if (query.isRsql()) {
            RSQLParser().parse(query).accept(TextPageRSQLVisitor())
        } else {
            buildGlobalTextPageSearch(query)
        }

        (TextPages innerJoin Texts)
            .selectAll()
            .where { expression or CustomIlikeOp(Texts.title, stringParam("%$query%")) }
            .map { it.toTextPage() }
    }

    private fun buildGlobalTextPageSearch(query: String): Op<Boolean> {
        val p = "%$query%"
        return listOf<Op<Boolean>>(
            CustomIlikeOp(TextPages.pureText, stringParam(p)),
            CustomIlikeOp(TextPages.glossedTextXML, stringParam(p)),
            CustomIlikeOp(TextPages.translationsXML, stringParam(p))
        ).reduce { acc, op -> acc or op }
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

private class TextPageRSQLVisitor : BaseRSQLVisitor() {
    override fun visit(node: ComparisonNode): Op<Boolean> {
        val selector = node.selector
        val arg = node.arguments[0]
        return when (selector) {
            "gloss" -> applyTextComparison(TextPages.glossedTextXML, node.operator, arg)
            "pureText" -> applyTextComparison(TextPages.pureText, node.operator, arg)
            "translation" -> applyTextComparison(TextPages.translationsXML, node.operator, arg)
            else -> throw IllegalArgumentException("Unknown field: $selector")
        }
    }
}