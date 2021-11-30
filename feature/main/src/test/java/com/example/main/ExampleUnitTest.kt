package com.example.main

import com.example.main.ui.options.tune.constants.ContrastConstants
import com.example.main.ui.options.convertValueToPercentage
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(0, convertValueToPercentage(1f, ContrastConstants))
    }

    @Test
    fun addition_isCorrect2() {
        assertEquals(100, convertValueToPercentage(3f, ContrastConstants))
    }
}