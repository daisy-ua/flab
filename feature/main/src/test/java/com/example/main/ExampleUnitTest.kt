package com.example.main

import flab.editor.library.constants.BrightnessConstants
import com.example.main.ui.options.convertPercentageToValue
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
        assertEquals(-100, convertPercentageToValue(-100f, BrightnessConstants))
    }
}