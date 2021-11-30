package com.example.main.ui.options.color

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.main.constants.HueConstants
import com.example.main.constants.SaturationConstants
import com.example.main.constants.ValueConstants
import com.example.main.ui.options.convertPercentageToValue
import flab.editor.library.ImageProcessManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ColorViewModel() : ViewModel() {
    var source by mutableStateOf<Bitmap?>(null)

    var processManager: ImageProcessManager? = null

    suspend fun setColorTransform(hue: Float, saturation: Float, value: Float) {
        withContext(Dispatchers.Main) {
            val bitmap = withContext(Dispatchers.Default) {
                processManager?.hsvTransform?.setHSVTransform(
                    convertPercentageToValue(hue, HueConstants),
                    convertPercentageToValue(saturation, SaturationConstants),
                    convertPercentageToValue(value, ValueConstants)
                )
            }

            source = bitmap
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("rita", "clear color")
    }
}