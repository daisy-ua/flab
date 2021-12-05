package com.example.imagesource

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesource.utils.FlipRotateCounter
import com.example.imagesource.utils.convertToBitmap
import com.example.imagesource.utils.getScaledDownBitmap
import flab.editor.library.ImageProcessManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val MAX_DIM_BITMAP = 900

class SourceViewModel(
    applicationContext: Application,
) : AndroidViewModel(applicationContext) {
    var processManager: ImageProcessManager? = null

    private var sourceUri: Uri? = null

    var currentSource by mutableStateOf<Bitmap?>(null)

    val getOriginalSource: suspend () -> Bitmap? = {
        withContext(Dispatchers.Default) {
            sourceUri?.let { uri ->
                convertToBitmap(applicationContext, uri)
            }
        }
    }

    var originalSource: Bitmap? = null

    fun setupBitmap(uri: Uri) = viewModelScope.launch(Dispatchers.Main) {
        sourceUri = uri
        originalSource = getOriginalSource()
        currentSource = originalSource?.let { src ->
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
    var flipRotateCounter: FlipRotateCounter? = null

    fun resetAdjust() {
        contrastValue = null
        brightnessValue = null
        hueValue = null
        saturationValue = null
        valueValue = null
        sharpnessValue = null
    }

    suspend fun applyColorTransforms(
        contrast: Double? = contrastValue,
        brightness: Double? = brightnessValue,
        hue: Double? = hueValue,
        saturation: Double? = saturationValue,
        value: Double? = valueValue,
        src: Bitmap? = null,
    ): Bitmap? {
        var source = src ?: processManager?.originalSource
        return withContext(Dispatchers.Default) {

            contrast?.let { contrastValue ->
                brightness?.let { brightnessValue ->
                    source = processManager?.applyLinearTransform(
                        contrastValue,
                        brightnessValue,
                        source
                    )
                }
            }

            hue?.let { hueValue ->
                saturation?.let { saturationValue ->
                    value?.let { valueValue ->
                        source = processManager?.applyHSVTransform(
                            hueValue,
                            saturationValue,
                            valueValue,
                            source
                        )
                    }
                }
            }
            return@withContext source
        }
    }

    suspend fun applyTransforms(
        contrast: Double? = contrastValue,
        brightness: Double? = brightnessValue,
        hue: Double? = hueValue,
        saturation: Double? = saturationValue,
        value: Double? = valueValue,
        src: Bitmap? = null,
    ): Bitmap? {
        var source = applyColorTransforms(contrast, brightness, hue, saturation, value, src)
        return withContext(Dispatchers.Default) {
            sharpnessValue?.let { sharpnessValue ->
                processManager?.updateSharpness(source)
                source = processManager?.applySharpness(sharpnessValue, source)

            }
            return@withContext source
        }
    }

    suspend fun applyFlipRotate(source: Bitmap): Bitmap? {
        return withContext(Dispatchers.Default) {
            processManager?.let { processManager ->
                flipRotateCounter?.let { flipRotateCounter ->
                    var original = source
                    processManager.updateFlipRotate(original)

                    for (direction in flipRotateCounter.rotateDirections) {
                        if (direction.name == flipRotateCounter.direction.name) {
                            break
                        }
                        original = processManager.applyRotate()
                    }

                    if (flipRotateCounter.isFlipped) {
                        processManager.updateFlipRotate(original)
                        original = processManager.applyFlip()
                    }
                    return@withContext original
                }
            }
        }
    }

    suspend fun getBitmapForSave(): Bitmap? {
        var src = originalSource
        src?.let {
            src = applyFlipRotate(source = it)
        }
        src = applyTransforms(src = src)
        return src
    }
}