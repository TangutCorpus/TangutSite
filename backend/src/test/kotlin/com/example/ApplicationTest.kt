package com.example

import com.example.routes.MainPageRoutesTest
import kotlin.test.Test

open class ApplicationTest {

    @Test
    fun runAllTests() {
        MainPageRoutesTest().apply{
            `test GET main page returns OK`()
        }
    }
}

