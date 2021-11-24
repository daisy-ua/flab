package com.example.main.ui.options.sharpness

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.main.constants.GaussianKernelConstants
import com.example.main.ui.options.convertPercentageToValue

class SharpnessViewModel(bitmap: Bitmap) : ViewModel() {
    var source by mutableStateOf(bitmap)
        private set

    private val sharpness = flab.editor.library.adjust.Sharpness(source)

    fun setSharpness(value: Float) {
        val kernelSize = getConvertedKernelSize(value)
        source = getSharpenedBitmap(kernelSize)
    }

    private fun getSharpenedBitmap(kernelSize: Double) : Bitmap {
        return if (kernelSize > 0)
            sharpness.sharpen(kernelSize)
        else sharpness.blur(-kernelSize)
    }

    private fun getConvertedKernelSize(value: Float): Double {
        var kernelSize = convertPercentageToValue(value, GaussianKernelConstants)
        if (kernelSize.toInt() % 2 == 0)
            kernelSize += 1.0
        return kernelSize
    }
}