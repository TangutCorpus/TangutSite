package com.example.helpers

import com.example.config.configureExceptionHandling
import com.example.config.configureHTTP
import com.example.config.configureMonitoring
import com.example.config.configureRouting
import com.example.config.configureSecurity
import com.example.config.configureSerialization
import com.example.model.Text
import com.example.service.TextService
import com.example.service.UserService
import io.ktor.server.application.Application
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.ApplicationTestBuilder
import io.mockk.coEvery
import io.mockk.mockk

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

    configureRouting(mockUserService, mockTextService)
    configureExceptionHandling()
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureSecurity()
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