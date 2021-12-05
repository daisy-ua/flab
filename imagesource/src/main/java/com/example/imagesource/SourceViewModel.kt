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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val MAX_DIM_BITMAP = 900

class SourceViewModel(
    applicationContext: Application,
) : AndroidViewModel(applicationContext) {
    var processManager: ImageProcessManager? = null

    private var sourceUri: Uri? = null

    val getOriginalSource: () -> Bitmap? = {
        sourceUri?.let { uri ->
            convertToBitmap(applicationContext, uri)
        }
    }

    var currentSource by mutableStateOf<Bitmap?>(null)

    fun setupBitmap(uri: Uri) {
        sourceUri = uri
        currentSource = getOriginalSource()?.let { src ->
            getScaledDownBitmap(src, MAX_DIM_BITMAP, true)
        }

        currentSource?.let { src ->
            processManager = ImageProcessManager(src)
        }
    }

    var contrastValue by mutableStateOf<Double?>(null)
    var brightnessValue by mutableStateOf<Double?>(null)
    var hueValue by mutableStateOf<Double?>(null)
    var saturationValue by mutableStateOf<Double?>(null)
    var valueValue by mutableStateOf<Double?>(null)
    var sharpnessValue by mutableStateOf<Double?>(null)

    suspend fun resetSource(
        source: Bitmap,
        contrast: Double? = contrastValue,
        brightness: Double? = brightnessValue,
        hue: Double? = hueValue,
        saturation: Double? = saturationValue,
        value: Double? = valueValue,
        sharpness: Double? = sharpnessValue,
    ): Bitmap? {
        return withContext(Dispatchers.Default) {
            processManager?.updateSource(contrast,
                brightness,
                hue,
                saturation,
                value,
                sharpness,
                source = source)
        }
    }

    suspend fun getHSVBitmap() : Bitmap? {
        return withContext(Dispatchers.Default) {
            processManager?.applyHSVTransform(
                hueValue, saturationValue, valueValue, processManager!!.originalSource
            )
        }
    }

    suspend fun getTuneBitmap() : Bitmap? {
        return withContext(Dispatchers.Default) {
            processManager?.applyLinearTransform(
                contrastValue, brightnessValue
            )
        }
    }
}