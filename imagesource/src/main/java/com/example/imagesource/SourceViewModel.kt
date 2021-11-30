package com.example.imagesource

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
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

    private val originalSource: Bitmap? by lazy {
        sourceUri?.let { uri ->
            convertToBitmap(applicationContext, uri)
        }
    }

    private var minimizedSource: Bitmap? = null

//    var currentSource: Bitmap? = null
//    var recompose by mutableStateOf(false)
    var currentSource by mutableStateOf<Bitmap?>(null)

//    fun setCurrent(bitmap: Bitmap?) {
//        currentSource = bitmap
//    }

    fun setupBitmap(uri: Uri) {
        Log.d("rita", "in setup")
        sourceUri = uri
        currentSource = originalSource?.let { src ->
            getScaledDownBitmap(src, MAX_DIM_BITMAP, true)
        }
//        currentSource = minimizedSource

        currentSource?.let { src ->
            processManager = ImageProcessManager(src)
        }
    }

    var contrastValue by mutableStateOf<Double?>(null)
    var brightnessValue by mutableStateOf<Double?>(null)
    var hueValue by mutableStateOf<Double?>(null)
    var saturationValue by mutableStateOf<Double?>(null)
    var valueValue by mutableStateOf<Double?>(null)

    suspend fun updateSource(
        contrast: Double? = contrastValue,
        brightness: Double? = brightnessValue,
        hue: Double? = hueValue,
        saturation: Double? = saturationValue,
        value: Double? = valueValue,
    ) {
        withContext(Dispatchers.Main) {
            val bitmap = withContext(Dispatchers.Default) {
                processManager?.updateSource(contrast, brightness, hue, saturation, value)
            }
            currentSource = bitmap
        }
    }
}