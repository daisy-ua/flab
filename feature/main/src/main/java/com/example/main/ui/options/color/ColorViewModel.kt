package com.example.main.ui.options.color

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.main.constants.HueConstants
import com.example.main.constants.SaturationConstants
import com.example.main.constants.ValueConstants
import com.example.main.ui.options.convertPercentageToValue
import flab.editor.library.adjust.HSVTransform

class ColorViewModel(bitmap: Bitmap) : ViewModel() {
    var source by mutableStateOf(bitmap)
        private set

    private val color = HSVTransform(source)

    fun setColorTransform(hue: Float, saturation: Float, value: Float) {
        source = color.setHSVTransform(
            convertPercentageToValue(hue, HueConstants),
            convertPercentageToValue(saturation, SaturationConstants),
            convertPercentageToValue(value, ValueConstants)
        )
    }
}