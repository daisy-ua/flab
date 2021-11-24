package com.example.main.ui.options.tune

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.main.constants.BrightnessConstants
import com.example.main.constants.ContrastConstants
import com.example.main.constants.GaussianKernelConstants
import com.example.main.ui.options.convertPercentageToValue
import flab.editor.library.adjust.Sharpness
import flab.editor.library.adjust.Tune

class TuneViewModel(bitmap: Bitmap) : ViewModel() {
    var source by mutableStateOf(bitmap)
        private set

    private val tune = Tune(source)
    private val sharpness = Sharpness(source)

    fun setBrightnessContrast(contrast: Float, brightness: Float) {
        source = tune.setBrightnessContrast(
            convertPercentageToValue(contrast, ContrastConstants),
            convertPercentageToValue(brightness, BrightnessConstants),
        )
    }

    fun setSharpness(value: Float) {
        val kernelSize = getConvertedKernelSize(value)
        source = if (kernelSize > 0)
            sharpness.sharpen(kernelSize)
        else sharpness.blur(-kernelSize)
    }

    private fun getConvertedKernelSize(value: Float) : Double {
        var kernelSize = convertPercentageToValue(value, GaussianKernelConstants)
        if (kernelSize.toInt() % 2 == 0)
            kernelSize += 1.0
        return kernelSize
    }
}