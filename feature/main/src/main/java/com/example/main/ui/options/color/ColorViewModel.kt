package com.example.main.ui.options.color

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.main.ui.options.color.constants.HueConstants
import com.example.main.ui.options.color.constants.SaturationConstants
import com.example.main.ui.options.color.constants.ValueConstants
import com.example.main.ui.options.convertPercentageToValue
import flab.editor.library.adjust.HSVTransform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ColorViewModel() : ViewModel() {
    var source by mutableStateOf<Bitmap?>(null)

//    var processManager: ImageProcessManager? = null

    var hsvTransform: HSVTransform? = null


    fun setupProcessor(bitmap: Bitmap) {
        hsvTransform = HSVTransform(bitmap)
    }

    suspend fun setColorTransform(hue: Float, saturation: Float, value: Float) {
        withContext(Dispatchers.Main) {
            val bitmap = withContext(Dispatchers.Default) {
                hsvTransform?.setHSVTransform(
                    convertPercentageToValue(hue, HueConstants),
                    convertPercentageToValue(saturation, SaturationConstants),
                    convertPercentageToValue(value, ValueConstants)
                )
            }

            source = bitmap
        }
    }
}