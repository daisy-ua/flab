package com.example.main.ui.options.color

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.main.ui.options.ILayerProcessManager
import com.example.main.ui.options.color.constants.HueConstants
import com.example.main.ui.options.color.constants.SaturationConstants
import com.example.main.ui.options.color.constants.ValueConstants
import com.example.main.ui.options.convertPercentageToValue
import com.example.main.ui.options.convertValueToPercentage
import flab.editor.library.ImageProcessManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.reflect.KSuspendFunction0

class ColorViewModel : ViewModel(), ILayerProcessManager {
    var source by mutableStateOf<Bitmap?>(null)

    val hue = { value: Float -> convertPercentageToValue(value, HueConstants) }
    val saturation = { value: Float -> convertPercentageToValue(value, SaturationConstants) }
    val value = { value: Float -> convertPercentageToValue(value, ValueConstants) }

    val huePercentage =
        { value: Double -> convertValueToPercentage(value.toFloat(), HueConstants) }
    val saturationPercentage =
        { value: Double -> convertValueToPercentage(value.toFloat(), SaturationConstants) }
    val valuePercentage =
        { value: Double -> convertValueToPercentage(value.toFloat(), ValueConstants) }

    var tuneBitmap by mutableStateOf<Bitmap?>(null)
    var hsvBitmap: Bitmap? = null

    override lateinit var processManager: ImageProcessManager

    override suspend fun setup(
        processManager: ImageProcessManager,
        getInitialHSVBitmap: KSuspendFunction0<Bitmap?>,
        getInitialTuneBitmap: KSuspendFunction0<Bitmap?>,
    ) {
        setupProcessor(processManager)
        tuneBitmap = getInitialTuneBitmap() ?: originalSource
        hsvBitmap = getInitialHSVBitmap() ?: originalSource
    }

    override suspend fun setInitialSource(): Bitmap =
        mergeSource(tuneBitmap!!, hsvBitmap!!)

    suspend fun updateHSVBitmap(hue: Float, saturation: Float, value: Float) {
        withContext(Dispatchers.Main) {
            hsvBitmap = withContext(Dispatchers.Default) {
                processManager.applyHSVTransform(
                    convertPercentageToValue(hue, HueConstants),
                    convertPercentageToValue(saturation, SaturationConstants),
                    convertPercentageToValue(value, ValueConstants)
                )
            }
        }
    }
}