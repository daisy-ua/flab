package com.example.main.ui.options.tune

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.main.ui.options.convertPercentageToValue
import com.example.main.ui.options.tune.constants.BrightnessConstants
import com.example.main.ui.options.tune.constants.ContrastConstants
import flab.editor.library.adjust.Tune
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TuneViewModel : ViewModel() {
    var source by mutableStateOf<Bitmap?>(null)

    var tune: Tune? = null

    fun setupProcessor(bitmap: Bitmap) {
        tune = Tune(bitmap)
    }

    suspend fun setBrightnessContrast(contrast: Float, brightness: Float) {
        withContext(Dispatchers.Main) {
            val bitmap = withContext(Dispatchers.Default) {
                tune?.setBrightnessContrast(
                    convertPercentageToValue(contrast, ContrastConstants),
                    convertPercentageToValue(brightness, BrightnessConstants),
                )
            }

            source = bitmap
        }
    }
}