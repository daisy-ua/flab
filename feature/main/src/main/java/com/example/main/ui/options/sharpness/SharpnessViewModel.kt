package com.example.main.ui.options.sharpness

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.main.ui.options.ILayerProcessManager
import com.example.main.ui.options.convertPercentageToValue
import com.example.main.ui.options.sharpness.constants.GaussianKernelConstants
import flab.editor.library.ImageProcessManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.reflect.KSuspendFunction0

class SharpnessViewModel : ViewModel(), ILayerProcessManager {
    var source by mutableStateOf<Bitmap?>(null)

    val sharpness = { value: Float -> getConvertedKernelSize(value) }

    private var tuneBitmap: Bitmap? = null
    private var hsvBitmap: Bitmap? = null

    override lateinit var processManager: ImageProcessManager

    override suspend fun setup(
        processManager: ImageProcessManager,
        getInitialHSVBitmap: KSuspendFunction0<Bitmap?>,
        getInitialTuneBitmap: KSuspendFunction0<Bitmap?>,
    ) {
        setupProcessor(processManager)
        hsvBitmap = getInitialHSVBitmap() ?: originalSource
        tuneBitmap = getInitialTuneBitmap() ?: originalSource
    }

    override suspend fun setInitialSource(): Bitmap =
        mergeSource(hsvBitmap!!, tuneBitmap!!)

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