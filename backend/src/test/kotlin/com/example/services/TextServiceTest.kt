package com.example.services

import com.example.model.Text
import com.example.repository.TextRepository
import com.example.service.TextService
import com.example.service.TextServiceImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TextServiceTest {
    private lateinit var service: TextService
    private val text = Text(1, "Text", listOf(0, 1, 2), "Text", Clock.System.todayIn(TimeZone.currentSystemDefault()))
    private val textWithEmptyFields =
        Text(1, "", listOf(0, 1, 2), "", Clock.System.todayIn(TimeZone.currentSystemDefault()))
    private val repository = mockk<TextRepository>(relaxed = true)

    @BeforeTest
    fun setup() {
        service = TextServiceImpl(repository)

        coEvery { repository.getTextById(1) } returns text
        coEvery { repository.getTextById(-1) } throws IllegalArgumentException("ID cannot be negative")
        coEvery { repository.getTextById(2) } returns null
        coEvery { repository.addText(text) } returns 1
        coEvery { repository.updateText(text) } returns 1
        coEvery { repository.deleteTextById(1) } returns 1
        coEvery { repository.getAllTexts() } returns listOf(text)
    }

    @Test
    fun `test get text by valid id returns text`() = runBlocking {
        assertEquals(text, service.getTextById(1))
    }

    @Test
    fun `test get text by invalid id throws exception`() = runBlocking {
        val exception = assertFailsWith<IllegalArgumentException> { service.getTextById(-1) }
        assertEquals("ID cannot be negative", exception.message)
    }

    @Test
    fun `test get text by non-existent id throws NoSuchElementException`() = runBlocking {
        val exception = assertFailsWith<NoSuchElementException> { service.getTextById(2) }
        assertEquals("No text found with id 2", exception.message)
    }

    @Test
    fun `test add valid text`() = runBlocking {
        assertDoesNotThrow { service.addText(text) }
        coVerify { repository.addText(text) }
    }

    @Test
    fun `test add text with empty fields throws exception`() = runBlocking {
        val exception = assertFailsWith<IllegalArgumentException> { service.addText(textWithEmptyFields) }
        assertEquals("Text cannot be empty", exception.message)
    }

    @Test
    fun `test update valid text`() = runBlocking {
        assertTrue(service.updateText(text))
    }

    @Test
    fun `test update text with empty fields throws exception`() = runBlocking {
        val exception = assertFailsWith<IllegalArgumentException> { service.updateText(textWithEmptyFields) }
        assertEquals("Text cannot be empty", exception.message)
    }

    @Test
    fun `test delete text by valid id`() = runBlocking {
        assertTrue(service.deleteTextById(1))
    }

    @Test
    fun `test delete text by invalid id throws exception`() = runBlocking {
        val exception = assertFailsWith<IllegalArgumentException> { service.deleteTextById(-1) }
        assertEquals("ID cannot be negative", exception.message)
    }

    @Test
    fun `test get all texts returns list of texts`() = runBlocking {
        assertEquals(listOf(text), service.getAllTexts())
    }
}
