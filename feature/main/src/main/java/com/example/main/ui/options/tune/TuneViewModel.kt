package com.example.main.ui.options.tune

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.main.ui.options.tune.constants.BrightnessConstants
import com.example.main.ui.options.tune.constants.ContrastConstants
import com.example.main.ui.options.utils.ILayerProcessManager
import com.example.main.ui.options.utils.convertPercentageToValue
import com.example.main.ui.options.utils.convertValueToPercentage
import flab.editor.library.ImageProcessManager

class TuneViewModel : ViewModel(), ILayerProcessManager {
    var source by mutableStateOf<Bitmap?>(null)

    val contrast = { value: Float -> convertPercentageToValue(value, ContrastConstants) }
    val brightness = { value: Float -> convertPercentageToValue(value, BrightnessConstants) }

    val brightnessPercentage =
        { value: Double -> convertValueToPercentage(value.toFloat(), BrightnessConstants) }
    val contrastPercentage =
        { value: Double -> convertValueToPercentage(value.toFloat(), ContrastConstants) }

    override lateinit var processManager: ImageProcessManager

    override suspend fun setup(
        processManager: ImageProcessManager,
        source: Bitmap?,
    ) {
        setupProcessor(processManager)
        this.source = source
    }
}