package com.example.main.ui.options.color

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.main.ui.options.color.constants.HueConstants
import com.example.main.ui.options.color.constants.SaturationConstants
import com.example.main.ui.options.color.constants.ValueConstants
import com.example.main.ui.options.utils.ILayerProcessManager
import com.example.main.ui.options.utils.convertPercentageToValue
import com.example.main.ui.options.utils.convertValueToPercentage
import flab.editor.library.ImageProcessManager

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

    override lateinit var processManager: ImageProcessManager

    override suspend fun setup(
        processManager: ImageProcessManager,
        source: Bitmap?,
    ) {
        setupProcessor(processManager)
        this.source = source
    }
}