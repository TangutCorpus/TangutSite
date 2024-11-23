package com.example.reposiotries

import com.example.helpers.getH2Database
import com.example.model.Text
import com.example.model.TextFragments
import com.example.model.Texts
import com.example.repository.TextRepository
import com.example.repository.TextRepositoryImpl
import cz.jirutka.rsql.parser.RSQLParserException
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.test.*

class TextRepositoryTest {
    private lateinit var repository: TextRepositoryImpl
    private val db = getH2Database()

    private val sampleText1 = Text(1, "Sample comment 1", listOf(1, 2), "Example text 1", null)
    private val sampleText2 = Text(2, "Sample comment 2", listOf(2, 3), "Example text 2", null)
    private val sampleText3 = Text(3, "Different comment", listOf(3, 4), "Additional text", null)

    @BeforeTest
    fun setup() {
        transaction(db) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.drop(TextFragments)
            SchemaUtils.drop(Texts)
            SchemaUtils.create(Texts)

            Texts.insert {
                it[comment] = sampleText1.comment
                it[lineIds] = sampleText1.lineIds.toString()
                it[pureText] = sampleText1.pureText
                it[createdAt] = null
            }
            Texts.insert {
                it[comment] = sampleText2.comment
                it[lineIds] = sampleText2.lineIds.toString()
                it[pureText] = sampleText2.pureText
                it[createdAt] = null
            }
            Texts.insert {
                it[comment] = sampleText3.comment
                it[lineIds] = sampleText3.lineIds.toString()
                it[pureText] = sampleText3.pureText
                it[createdAt] = null
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
        assertNotNull(repository.addText(sampleText1))
    }

    @Test
    fun `update existing text`() = runBlocking {
        assertEquals(1, repository.updateText(sampleText1))
    }

    @Test
    fun `delete text by invalid id does nothing`() = runBlocking {
        assertEquals(0, repository.deleteTextById(2222))
    }

    @Test
    fun `get texts by valid id equals operator`() = runBlocking {
        val result = repository.getTextsByQuery("id==1")
        assertEquals(listOf(sampleText1), result)
    }

    @Test
    fun `get texts by id not equal operator`() = runBlocking {
        val result = repository.getTextsByQuery("id!=1")
        assertEquals(listOf(sampleText2, sampleText3), result)
    }

    @Test
    fun `get texts by id less than operator`() = runBlocking {
        val result = repository.getTextsByQuery("id=lt=3")
        assertEquals(listOf(sampleText1, sampleText2), result)
    }

    @Test
    fun `get texts by id greater than operator`() = runBlocking {
        val result = repository.getTextsByQuery("id=gt=1")
        assertEquals(listOf(sampleText2, sampleText3), result)
    }

    @Test
    fun `get texts by exact match on comment`() = runBlocking {
        val result = repository.getTextsByQuery("comment=='Sample comment 1'")
        assertEquals(listOf(sampleText1), result)
    }

    @Test
    fun `get texts by comment not equal operator`() = runBlocking {
        val result = repository.getTextsByQuery("comment!='Sample comment 1'")
        assertEquals(listOf(sampleText2, sampleText3), result)
    }

    @Test
    fun `get texts by pureText contains operator`() = runBlocking {
        val result = repository.getTextsByQuery("pureText=in='text'")
        assertEquals(listOf(sampleText1, sampleText2, sampleText3), result)
    }

    @Test
    fun `get texts by combined id and comment query`() = runBlocking {
        val result = repository.getTextsByQuery("id==1;comment=in='Sample'")
        assertEquals(listOf(sampleText1), result)
    }

    @Test
    fun `get texts by id equals or comment contains`() = runBlocking {
        val result = repository.getTextsByQuery("id==1,comment=in='Different'")
        assertEquals(listOf(sampleText1, sampleText3), result)
    }

    @Test
    fun `unsupported operator throws exception`(): Unit = runBlocking {
        assertFailsWith<RSQLParserException> {
            repository.getTextsByQuery("comment=unsupported='Test'")
        }
    }

    @Test
    fun `invalid field in query throws exception`() = runBlocking {
        val exception = assertFailsWith<IllegalArgumentException> {
            repository.getTextsByQuery("unknownField==1")
        }
        assertEquals("Unknown field: unknownField", exception.message)
    }

    @Test
    fun `invalid integer argument throws exception`() = runBlocking {
        val exception = assertFailsWith<IllegalArgumentException> {
            repository.getTextsByQuery("id==abc")
        }
        assertEquals("Invalid integer: abc", exception.message)
    }
}
