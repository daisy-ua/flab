package com.example.main.ui.options.tune

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.main.constants.BrightnessConstants
import com.example.main.constants.ContrastConstants
import com.example.main.ui.options.convertPercentageToValue
import flab.editor.library.adjust.Tune

class TuneViewModel(bitmap: Bitmap) : ViewModel() {
    var source by mutableStateOf(bitmap)
        private set

    private val tune = Tune(source)

    fun setBrightnessContrast(contrast: Float, brightness: Float) {
        source = tune.setBrightnessContrast(
            convertPercentageToValue(contrast, ContrastConstants),
            convertPercentageToValue(brightness, BrightnessConstants),
        )
    }
}