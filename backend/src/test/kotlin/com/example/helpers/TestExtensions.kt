package com.example.helpers

import com.example.config.configureExceptionHandling
import com.example.config.configureHTTP
import com.example.config.configureMonitoring
import com.example.config.configureRouting
import com.example.config.configureSecurity
import com.example.config.configureSerialization
import com.example.model.Text
import com.example.service.SearchService
import com.example.service.TextService
import com.example.service.UserService
import io.ktor.server.application.Application
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.ApplicationTestBuilder
import io.mockk.coEvery
import io.mockk.mockk
import org.jetbrains.exposed.sql.Database

fun getH2Database(): Database {
    return Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
}

fun ApplicationTestBuilder.setupApp(defaultText: Text? = null) {
    environment {
        config = ApplicationConfig("application-test.conf")
    }
    application {
        testModule(defaultText)
    }
}

fun Application.testModule(defaultText: Text?) {
    val mockTextService = createMockTextService(defaultText)
    val mockUserService = createMockUserService()
    val mockSearchService = createMockSearchService(defaultText)

    configureRouting(mockUserService, mockTextService, mockSearchService)
    configureExceptionHandling()
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureSecurity()
}

private fun createMockSearchService(defaultText: Text?) = mockk<SearchService>(relaxed = true).apply {
    if (defaultText != null) {
        coEvery { search("comment=='Sample Comment' and pureText=='Sample Text'") } returns listOf(defaultText)
        coEvery { search("comment=='Nonexistent'") } returns emptyList()
        coEvery { returnAllSearchResults() } returns listOf(defaultText)
    }
}

private fun createMockTextService(defaultText: Text?) = mockk<TextService>(relaxed = true).apply {
    if (defaultText != null) {
        coEvery { addText(any()) } returns Unit
        coEvery { getTextById(any()) } returns null
        coEvery { getTextById(1) } returns defaultText
        coEvery { getAllTexts() } returns listOf(defaultText)
        coEvery { deleteTextById(1) } returns true
        coEvery { updateText(any()) } returns false
        coEvery { updateText(defaultText) } returns true
    }
}

private fun createMockUserService() = mockk<UserService>(relaxed = true)