package com.example.main.ui.options.sharpness

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.main.ui.options.sharpness.constants.GaussianKernelConstants
import com.example.main.ui.options.utils.ILayerProcessManager
import com.example.main.ui.options.utils.convertPercentageToValue
import flab.editor.library.ImageProcessManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SharpnessViewModel : ViewModel(), ILayerProcessManager {
    var source by mutableStateOf<Bitmap?>(null)

    val sharpness = { value: Float -> getConvertedKernelSize(value) }

    override lateinit var processManager: ImageProcessManager

    override suspend fun setup(
        processManager: ImageProcessManager,
        source: Bitmap?,
    ) {
        setupProcessor(processManager)
        this.source = source
    }

    suspend fun updateSource(value: Float) {
        withContext(Dispatchers.Main) {
            val kernelSize = getConvertedKernelSize(value)
            source = withContext(Dispatchers.Default) {
                processManager.applySharpness(kernelSize)
            }
        }
    }

    private fun getConvertedKernelSize(value: Float): Double {
        var kernelSize = convertPercentageToValue(value, GaussianKernelConstants)
        if (kernelSize.toInt() % 2 == 0)
            kernelSize += 1.0
        return kernelSize
    }
}