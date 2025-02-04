package com.example.services

import com.example.helpers.DefaultParams
import com.example.model.TextFragment
import com.example.repository.TextFragmentRepository
import com.example.service.TextFragmentService
import com.example.service.TextFragmentServiceImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.*
import kotlin.test.BeforeTest

class TextFragmentServiceTest {
    private lateinit var service: TextFragmentService
    private val repository = mockk<TextFragmentRepository>(relaxed = true)

    private val validFragment =
        TextFragment(DefaultParams.textFragmentId, DefaultParams.textID, 1, "Text", "Text", null)
    private val invalidFragment = TextFragment(DefaultParams.textFragmentId, DefaultParams.invalidId, 1, "", "", null)

    @BeforeTest
    fun setup() {
        service = TextFragmentServiceImpl(repository)

        coEvery { repository.getTextFragmentById(DefaultParams.textFragmentId) } returns validFragment
        coEvery { repository.getTextFragmentById(DefaultParams.invalidId) } returns null
        coEvery { repository.addTextFragment(validFragment) } returns DefaultParams.textFragmentId
        coEvery { repository.updateTextFragment(validFragment) } returns 1
        coEvery { repository.deleteTextFragmentById(DefaultParams.textFragmentId) } returns 1
        coEvery { repository.getAllTextFragments() } returns listOf(validFragment)
    }

    @Test
    fun `get text fragment by valid id returns text fragment`() = runBlocking {
        val result = service.getTextFragmentById(DefaultParams.textFragmentId)
        assertEquals(validFragment, result)
    }

    @Test
    fun `get text fragment by invalid id throws exception`() = runBlocking {
        val exception = assertFailsWith<NoSuchElementException> {
            service.getTextFragmentById(DefaultParams.invalidId)
        }
        assertEquals("No fragment found with id 2", exception.message)
    }

    @Test
    fun `add valid text fragment`() = runBlocking {
        assertDoesNotThrow { service.addTextFragment(validFragment) }
        coVerify { repository.addTextFragment(validFragment) }
    }

    @Test
    fun `add invalid text fragment throws exception`() = runBlocking {
        val exception = assertFailsWith<IllegalArgumentException> {
            service.addTextFragment(invalidFragment)
        }
        assertTrue(exception.message!!.contains("Fragment cannot be empty"))
    }

    @Test
    fun `update valid text fragment`() = runBlocking {
        assertTrue(service.updateTextFragment(validFragment))
    }

    @Test
    fun `update invalid text fragment throws exception`() = runBlocking {
        val exception = assertFailsWith<IllegalArgumentException> {
            service.updateTextFragment(invalidFragment)
        }
        assertTrue(exception.message!!.contains("Fragment cannot be empty"))
    }

    @Test
    fun `delete text fragment by valid id`() = runBlocking {
        assertTrue(service.deleteTextFragmentById(DefaultParams.textFragmentId))
    }

    @Test
    fun `delete text fragment by invalid id throws exception`() = runBlocking {
        val exception = assertFailsWith<IllegalArgumentException> {
            service.deleteTextFragmentById(DefaultParams.invalidId)
        }
        assertEquals("ID cannot be negative", exception.message)
    }

    @Test
    fun `get all text fragments returns list of fragments`() = runBlocking {
        val result = service.getAllTextFragments()
        assertEquals(listOf(validFragment), result)
    }
}
