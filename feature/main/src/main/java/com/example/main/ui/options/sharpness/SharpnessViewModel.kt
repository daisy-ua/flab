package com.example.main.ui.options.sharpness

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.main.ui.options.sharpness.constants.GaussianKernelConstants
import com.example.main.ui.options.convertPercentageToValue
import flab.editor.library.adjust.HSVTransform
import flab.editor.library.adjust.Sharpness
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SharpnessViewModel() : ViewModel() {
    var source by mutableStateOf<Bitmap?>(null)

    var sharpness: Sharpness? = null

    fun setupProcessor(bitmap: Bitmap) {
        sharpness = Sharpness(bitmap)
    }

    suspend fun setSharpness(value: Float) {
        val kernelSize = getConvertedKernelSize(value)
        source = getSharpenedBitmap(kernelSize)
    }

    private suspend fun getSharpenedBitmap(kernelSize: Double) : Bitmap? {
        return withContext(Dispatchers.Default) {
            if (kernelSize > 0)
                sharpness?.sharpen(kernelSize)
            else sharpness?.blur(-kernelSize)
        }
    }

    fun getConvertedKernelSize(value: Float): Double {
        var kernelSize = convertPercentageToValue(value, GaussianKernelConstants)
        if (kernelSize.toInt() % 2 == 0)
            kernelSize += 1.0
        return kernelSize
    }
}