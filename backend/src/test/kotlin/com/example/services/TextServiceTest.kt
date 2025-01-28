package com.example.services

import com.example.helpers.DefaultParams
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
    private val text = Text(DefaultParams.textID, listOf(DefaultParams.textFragmentId), "Text", "Text", Clock.System.todayIn(TimeZone.currentSystemDefault()))
    private val textWithEmptyFields =
        Text(DefaultParams.textID, listOf(DefaultParams.textFragmentId), "", "",  Clock.System.todayIn(TimeZone.currentSystemDefault()))
    private val repository = mockk<TextRepository>(relaxed = true)

    @BeforeTest
    fun setup() {
        service = TextServiceImpl(repository)

        coEvery { repository.getTextById(DefaultParams.textID) } returns text
        coEvery { repository.getTextById(DefaultParams.invalidId) } returns null
        coEvery { repository.addText(text) } returns DefaultParams.textID
        coEvery { repository.updateText(text) } returns 1
        coEvery { repository.deleteTextById(DefaultParams.textID) } returns 1
        coEvery { repository.getAllTexts() } returns listOf(text)
    }

    @Test
    fun `test get text by valid id returns text`() = runBlocking {
        assertEquals(text, service.getTextById(DefaultParams.textID))
    }

    @Test
    fun `test get text by non-existent id throws NoSuchElementException`() = runBlocking {
        val exception = assertFailsWith<NoSuchElementException> { service.getTextById(DefaultParams.invalidId) }
        assertTrue(exception.message?.startsWith("No text found with id") == true)
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
        assertTrue(service.deleteTextById(DefaultParams.textID))
    }

    @Test
    fun `test get all texts returns list of texts`() = runBlocking {
        assertEquals(listOf(text), service.getAllTexts())
    }
}
