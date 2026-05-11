package com.example.repository

import com.example.model.*
import com.example.utils.toUUIDOrNull
import cz.jirutka.rsql.parser.RSQLParser
import cz.jirutka.rsql.parser.ast.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class DictionaryRepository(private val db: Database) {
    init {
        transaction(db) {
            SchemaUtils.create(DictionaryArticles)
        }
    }

    fun getArticleById(id: UUID): DictionaryArticle? = transaction(db) {
        DictionaryArticles.selectAll().where { DictionaryArticles.id eq id }
            .mapNotNull { it.toDictionaryArticle() }.singleOrNull()
    }

    fun getArticlesByQuery(query: String): List<DictionaryArticle> = transaction(db) {
        DictionaryArticles.selectAll().searchArticles(query).mapNotNull { it.toDictionaryArticle() }
    }

    fun addArticle(article: DictionaryArticle) = transaction(db) {
        DictionaryArticles.insert {
            it[id] = article.id
            it[character] = article.character
            it[unicodeCode] = article.unicodeCode
            it[unicodeKey] = article.unicodeKey
            it[strokeCountUnicode] = article.strokeCountUnicode
            it[strokeCountTotal] = article.strokeCountTotal
            it[tone] = article.tone
            it[seaOfWritingAnalysis] = article.seaOfWritingAnalysis
            it[components] = article.components
            it[reconstructions] = article.reconstructions
            it[dictionaryReferences] = article.dictionaryReferences
            it[initials] = article.initials
            it[rhymes] = article.rhymes
            it[fanqie] = article.fanqie
            it[chineseCharacters] = article.chineseCharacters
            it[tibetanSyllables] = article.tibetanSyllables
            it[sanskritSyllables] = article.sanskritSyllables
            it[imageGroups] = article.imageGroups
            it[compoundWords] = article.compoundWords
            it[relatedWords] = article.relatedWords
            it[corpusExamples] = article.corpusExamples
        } get DictionaryArticles.id
    }

    fun updateArticle(article: DictionaryArticle) = transaction(db) {
        DictionaryArticles.update({ DictionaryArticles.id eq article.id }) {
            it[character] = article.character
            it[unicodeCode] = article.unicodeCode
            it[unicodeKey] = article.unicodeKey
            it[strokeCountUnicode] = article.strokeCountUnicode
            it[strokeCountTotal] = article.strokeCountTotal
            it[tone] = article.tone
            it[seaOfWritingAnalysis] = article.seaOfWritingAnalysis
            it[components] = article.components
            it[reconstructions] = article.reconstructions
            it[dictionaryReferences] = article.dictionaryReferences
            it[initials] = article.initials
            it[rhymes] = article.rhymes
            it[fanqie] = article.fanqie
            it[chineseCharacters] = article.chineseCharacters
            it[tibetanSyllables] = article.tibetanSyllables
            it[sanskritSyllables] = article.sanskritSyllables
            it[imageGroups] = article.imageGroups
            it[compoundWords] = article.compoundWords
            it[relatedWords] = article.relatedWords
            it[corpusExamples] = article.corpusExamples
        }
    }

    fun deleteArticleById(id: UUID) = transaction(db) {
        DictionaryArticles.deleteWhere { DictionaryArticles.id eq id }
    }

    fun getAllArticles(): List<DictionaryArticle> = transaction(db) {
        DictionaryArticles.selectAll().map { it.toDictionaryArticle() }
    }
}

private fun ResultRow.toDictionaryArticle() = DictionaryArticle(
    id = this[DictionaryArticles.id],
    character = this[DictionaryArticles.character],
    unicodeCode = this[DictionaryArticles.unicodeCode],
    unicodeKey = this[DictionaryArticles.unicodeKey],
    strokeCountUnicode = this[DictionaryArticles.strokeCountUnicode],
    strokeCountTotal = this[DictionaryArticles.strokeCountTotal],
    components = this[DictionaryArticles.components],
    seaOfWritingAnalysis = this[DictionaryArticles.seaOfWritingAnalysis],
    reconstructions = this[DictionaryArticles.reconstructions],
    dictionaryReferences = this[DictionaryArticles.dictionaryReferences],
    initials = this[DictionaryArticles.initials],
    tone = this[DictionaryArticles.tone],
    rhymes = this[DictionaryArticles.rhymes],
    fanqie = this[DictionaryArticles.fanqie],
    chineseCharacters = this[DictionaryArticles.chineseCharacters],
    tibetanSyllables = this[DictionaryArticles.tibetanSyllables],
    sanskritSyllables = this[DictionaryArticles.sanskritSyllables],
    imageGroups = this[DictionaryArticles.imageGroups],
    compoundWords = this[DictionaryArticles.compoundWords],
    relatedWords = this[DictionaryArticles.relatedWords],
    corpusExamples = this[DictionaryArticles.corpusExamples]
)

fun Query.searchArticles(query: String): Query {
    val rootNode: Node = RSQLParser().parse(query)
    val queryExpression = rootNode.accept(DictionaryRSQLVisitor())
    return this.andWhere { queryExpression }
}

private class DictionaryRSQLVisitor : NoArgRSQLVisitorAdapter<Op<Boolean>>() {
    override fun visit(node: AndNode): Op<Boolean> = node.children.map { it.accept(this) }.reduce { acc, op -> acc and op }
    override fun visit(node: OrNode): Op<Boolean> = node.children.map { it.accept(this) }.reduce { acc, op -> acc or op }

    override fun visit(node: ComparisonNode): Op<Boolean> {
        val selector = node.selector
        val operator = node.operator
        val argument = node.arguments[0]

        return when (selector) {
            "id" -> applyUUIDComparison(DictionaryArticles.id, operator, argument)
            "character" -> applyTextComparison(DictionaryArticles.character, operator, argument)
            "unicodeCode" -> applyTextComparison(DictionaryArticles.unicodeCode, operator, argument)
            "tone" -> applyTextComparison(DictionaryArticles.tone, operator, argument)
            else -> throw IllegalArgumentException("Unknown field: $selector")
        }
    }

    fun applyUUIDComparison(col: Column<UUID>, op: ComparisonOperator, arg: String): Op<Boolean> {
        val uuid = arg.toUUIDOrNull() ?: throw IllegalArgumentException("Invalid UUID")
        return if (op.symbol == "==") col eq uuid else col neq uuid
    }

    fun applyTextComparison(col: Column<String>, op: ComparisonOperator, arg: String): Op<Boolean> {
        return when (op.symbol) {
            "==" -> col eq arg
            "=in=" -> col like "%$arg%"
            else -> col neq arg
        }
    }
}