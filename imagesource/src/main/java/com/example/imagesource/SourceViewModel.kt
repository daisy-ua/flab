package com.example.imagesource

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.imagesource.utils.convertToBitmap
import com.example.imagesource.utils.getScaledDownBitmap
import flab.editor.library.ImageProcessManager

private const val MAX_DIM_BITMAP = 900

class SourceViewModel(
    applicationContext: Application,
) : AndroidViewModel(applicationContext) {
    lateinit var processManager: ImageProcessManager

    private var sourceUri: Uri? = null

    private val originalSource: Bitmap? by lazy {
        sourceUri?.let { uri ->
            convertToBitmap(applicationContext, uri)
        }
    }

    private var minimizedSource: Bitmap? = null

    var currentSource by mutableStateOf<Bitmap?>(null)

    fun setupBitmap(uri: Uri) {
        sourceUri = uri
        minimizedSource = originalSource?.let { src ->
            getScaledDownBitmap(src, MAX_DIM_BITMAP, true)
        }
        currentSource = minimizedSource

        minimizedSource?.let { src ->
            processManager = ImageProcessManager(src)
        }
    }

    var contrastValue = 1.0
    var brightnessValue = 0.0
    var hueValue = 100.0
    var saturationValue = 100.0
    var valueValue = 0.0

    fun updateSource(
        contrast: Double? = contrastValue,
        brightness: Double? = brightnessValue,
        hue: Double? = hueValue,
        saturation: Double? = saturationValue,
        value: Double? = valueValue,
    ) {
        currentSource = processManager.updateSource(contrast, brightness, hue, saturation, value)
    }
}