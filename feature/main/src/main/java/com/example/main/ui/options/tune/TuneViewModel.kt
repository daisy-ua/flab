package com.example.main.ui.options.tune

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.main.ui.options.ILayerProcessManager
import com.example.main.ui.options.convertPercentageToValue
import com.example.main.ui.options.convertValueToPercentage
import com.example.main.ui.options.tune.constants.BrightnessConstants
import com.example.main.ui.options.tune.constants.ContrastConstants
import flab.editor.library.ImageProcessManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.reflect.KSuspendFunction0

class TuneViewModel : ViewModel(), ILayerProcessManager {
    var source by mutableStateOf<Bitmap?>(null)

    val contrast = { value: Float -> convertPercentageToValue(value, ContrastConstants) }
    val brightness = { value: Float -> convertPercentageToValue(value, BrightnessConstants) }

    val brightnessPercentage =
        { value: Double -> convertValueToPercentage(value.toFloat(), BrightnessConstants) }
    val contrastPercentage =
        { value: Double -> convertValueToPercentage(value.toFloat(), ContrastConstants) }


    var hsvBitmap by mutableStateOf<Bitmap?>(null)
    var tuneBitmap: Bitmap? = null

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

    override suspend fun setInitialSource(): Bitmap = mergeSource(hsvBitmap!!, tuneBitmap!!)

    suspend fun updateTuneBitmap(contrast: Float, brightness: Float) {
        withContext(Dispatchers.Main) {
            tuneBitmap = withContext(Dispatchers.Default) {
                processManager.applyLinearTransform(
                    convertPercentageToValue(contrast, ContrastConstants),
                    convertPercentageToValue(brightness, BrightnessConstants),
                )
            }
        }
    }
}