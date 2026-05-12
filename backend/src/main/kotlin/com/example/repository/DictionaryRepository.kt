package com.example.repository

import com.example.model.DictionaryArticle
import com.example.model.DictionaryArticles
import com.example.utils.BaseRSQLVisitor
import com.example.utils.CustomIlikeOp
import com.example.utils.CustomJsonbIlikeOp
import com.example.utils.isRsql
import cz.jirutka.rsql.parser.RSQLParser
import cz.jirutka.rsql.parser.ast.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class DictionaryRepository(private val db: Database) {
    init {
        transaction(db) {
            SchemaUtils.create(DictionaryArticles)
        }
    }

    fun getArticleById(id: UUID): DictionaryArticle? = transaction(db) {
        DictionaryArticles.selectAll().where { DictionaryArticles.id eq id }.mapNotNull { it.toDictionaryArticle() }
            .singleOrNull()
    }

    fun getArticlesByQuery(query: String): List<DictionaryArticle> = transaction(db) {
        val expression = if (query.isRsql()) {
            RSQLParser().parse(query).accept(DictionaryRSQLVisitor())
        } else {
            buildGlobalDictionarySearch(query)
        }
        DictionaryArticles.selectAll().where { expression }.map { it.toDictionaryArticle() }
    }

    private fun buildGlobalDictionarySearch(query: String): Op<Boolean> {
        val p = "%$query%"
        return listOf<Op<Boolean>>(
            CustomIlikeOp(DictionaryArticles.character, stringParam(p)),
            CustomIlikeOp(DictionaryArticles.seaOfWritingAnalysis, stringParam(p)),
            CustomJsonbIlikeOp(DictionaryArticles.components, p),
            CustomJsonbIlikeOp(DictionaryArticles.corpusExamples, p)
        ).reduce { acc, op -> acc or op }
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

private class DictionaryRSQLVisitor : BaseRSQLVisitor() {
    override fun visit(node: ComparisonNode): Op<Boolean> {
        val selector = node.selector
        val arg = node.arguments[0]
        return when (selector) {
            "id" -> applyUUIDComparison(DictionaryArticles.id, node.operator, arg)
            "character" -> applyTextComparison(DictionaryArticles.character, node.operator, arg)
            "components" -> CustomJsonbIlikeOp(DictionaryArticles.components, "%$arg%")
            "pureText" -> applyTextComparison(DictionaryArticles.seaOfWritingAnalysis, node.operator, arg)
            else -> throw IllegalArgumentException("Unknown field: $selector")
        }
    }
}