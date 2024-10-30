package com.example.reposiotries

import com.example.model.TextFragment
import com.example.model.TextFragments
import com.example.model.Texts
import com.example.repository.TextFragmentRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import kotlin.properties.Delegates
import kotlin.test.*

class TextFragmentRepositoryTest {
    private lateinit var repository: TextFragmentRepositoryImpl
    private val db = Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")

    private var textId by Delegates.notNull<Int>()
    private val sampleFragment = TextFragment(null, 0, 1, "Test", "Test", null)

    @BeforeTest
    fun setup() {
        transaction(db) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.drop(TextFragments)
            SchemaUtils.drop(Texts)
            SchemaUtils.create(Texts, TextFragments)

            textId = Texts.insert {
                it[comment] = "Text"
                it[lineIds] = listOf(1).toString()
                it[pureText] = "Text"
                it[createdAt] = LocalDateTime.now()
            } get Texts.id
        }
        repository = TextFragmentRepositoryImpl(db)
    }

    @Test
    fun `addTextFragment inserts and returns new fragment ID`() = runBlocking {
        val fragmentId = repository.addTextFragment(sampleFragment.copy(textId = textId))
        assertNotNull(fragmentId)

        val retrieved = repository.getTextFragmentById(fragmentId)
        assertNotNull(retrieved)
        assertEquals(sampleFragment.contentXML, retrieved.contentXML)
        assertEquals(sampleFragment.commentXML, retrieved.commentXML)
    }

    @Test
    fun `getTextFragmentById returns correct fragment`() = runBlocking {
        val fragmentId = repository.addTextFragment(sampleFragment.copy(textId = textId))
        val fragment = repository.getTextFragmentById(fragmentId)

        assertNotNull(fragment)
        assertEquals(sampleFragment.contentXML, fragment.contentXML)
        assertEquals(sampleFragment.lineNumber, fragment.lineNumber)
    }

    @Test
    fun `updateTextFragment modifies existing fragment`() = runBlocking {
        val fragmentId = repository.addTextFragment(sampleFragment.copy(textId = textId))
        val updatedFragment = sampleFragment.copy(
            id = fragmentId, textId = textId, contentXML = "Updated"
        )

        repository.updateTextFragment(updatedFragment)
        val retrieved = repository.getTextFragmentById(fragmentId)

        assertNotNull(retrieved)
        assertEquals(updatedFragment.contentXML, retrieved.contentXML)
        assertEquals(updatedFragment.commentXML, retrieved.commentXML)
    }

    @Test
    fun `deleteTextFragmentById removes fragment`() = runBlocking {
        val fragmentId = repository.addTextFragment(sampleFragment.copy(textId = textId))
        repository.deleteTextFragmentById(fragmentId)
        val deletedFragment = repository.getTextFragmentById(fragmentId)
        assertNull(deletedFragment)
    }

    @Test
    fun `cascade delete removes fragments when related text is deleted`() = runBlocking {
        val fragmentId = repository.addTextFragment(sampleFragment.copy(textId = textId))
        transaction(db) {
            Texts.deleteWhere { id eq textId }
        }
        val orphanedFragment = repository.getTextFragmentById(fragmentId)
        assertNull(orphanedFragment)
    }
}
