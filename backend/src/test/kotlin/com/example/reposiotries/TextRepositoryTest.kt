package com.example.reposiotries

import com.example.model.Text
import com.example.model.TextFragments
import com.example.model.Texts
import com.example.repository.TextRepository
import com.example.repository.TextRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.test.*

class TextRepositoryTest {
    private lateinit var repository: TextRepository
    private val db = Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")

    private val sampleText = Text(1, "Text", listOf(1), "Text", null)

    @BeforeTest
    fun setup() {
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.drop(TextFragments)
            SchemaUtils.drop(Texts)
            SchemaUtils.create(Texts)
            Texts.insert {
                it[comment] = sampleText.comment
                it[createdAt] = null
                it[lineIds] = sampleText.lineIds.toString()
                it[pureText] = sampleText.pureText
            }
        }
        repository = TextRepositoryImpl(db)
    }

    @Test
    fun `get text by valid id returns text`(): Unit = runBlocking {
        assertNotNull(repository.getTextById(1))
        assertEquals(1, repository.deleteTextById(1))
    }

    @Test
    fun `get text by invalid id returns null`() = runBlocking {
        assertNull(repository.getTextById(22222))
    }

    @Test
    fun `add valid text returns generated id`(): Unit = runBlocking {
        assertNotNull(repository.addText(sampleText))
    }

    @Test
    fun `update existing text`() = runBlocking {
        assertEquals(1, repository.updateText(sampleText))
    }

    @Test
    fun `delete text by invalid id does nothing`() = runBlocking {
        assertEquals(0, repository.deleteTextById(2222))
    }
}
