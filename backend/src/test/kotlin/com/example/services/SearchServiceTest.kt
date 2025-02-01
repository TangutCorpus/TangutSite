package com.example.services

import com.example.helpers.DefaultParams
import com.example.model.Text
import com.example.repository.TextRepository
import com.example.service.SearchServiceImpl
import io.mockk.*
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchServiceTest {
    private lateinit var service: SearchServiceImpl
    private val textRepository = mockk<TextRepository>(relaxed = true)
    private val text = Text(
        DefaultParams.textID,
        listOf(DefaultParams.textFragmentId),
        "Example comment 1",
        "Some text here",
        Clock.System.todayIn(TimeZone.currentSystemDefault())
    )

    @BeforeTest
    fun setup() {
        service = SearchServiceImpl(textRepository)

        coEvery { textRepository.getAllTexts() } returns listOf(text)
        coEvery { textRepository.getTextsByQuery("test") } returns listOf(text)
    }

    @Test
    fun `test get all texts returns texts`() = runBlocking {
        assertEquals(listOf(text), service.returnAllSearchResults())
    }

    @Test
    fun `test get texts by query returns texts`() = runBlocking {
        assertEquals(listOf(text), service.search("test"))
    }
}